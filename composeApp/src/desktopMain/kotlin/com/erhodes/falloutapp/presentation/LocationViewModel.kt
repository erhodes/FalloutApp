package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.campaign.BasicFeature
import com.erhodes.falloutapp.model.campaign.BasicLocation
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.PurchaseResult
import com.erhodes.falloutapp.model.campaign.StockEntry
import com.erhodes.falloutapp.model.campaign.Store
import com.erhodes.falloutapp.repository.CampaignRepository
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class LocationViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel(), KoinComponent {
    private val repo: LocationRepository by inject()

    // A purchase mutates a store (inside the location) and the buyer, which persist through
    // different repositories, so this ViewModel is where the two halves are driven together.
    private val characterRepo: CharacterRepository by inject()

    private val campaignRepo: CampaignRepository by inject()

    /** The location the GM is viewing/editing. Not necessarily where the players are. */
    private var activeLocation: Location = repo.activeLocation

    /** Id of the active campaign's current location, i.e. where the players are. */
    private val _currentLocationId = MutableStateFlow(campaignRepo.activeCampaign.currentLocation.id)
    val currentLocationId = _currentLocationId.asStateFlow()

    /** Which of the location's stores the store screen is showing, if any. */
    private var activeStoreId: String? = null

    private val _activeLocationState = MutableStateFlow(buildState())
    val activeLocationState = _activeLocationState.asStateFlow()

    private val _activeStoreState = MutableStateFlow(buildStoreState())
    val activeStoreState = _activeStoreState.asStateFlow()

    private val _buyers = MutableStateFlow(buildBuyers())
    val buyers = _buyers.asStateFlow()

    /** The outcome of the last purchase attempt, for the store screen to show and then dismiss. */
    private val _purchaseMessage = MutableStateFlow<String?>(null)
    val purchaseMessage = _purchaseMessage.asStateFlow()

    private val _savedLocations = MutableStateFlow<List<Location>>(emptyList())
    val savedLocations = _savedLocations.asStateFlow()

    fun onSelectLocation(location: Location) {
        repo.setActiveLocation(location)
        activeLocation = location
        activeStoreId = null
        publishState()
    }

    fun onNewLocation() {
        activeLocation = BasicLocation("New Location")
        repo.setActiveLocation(activeLocation)
        activeStoreId = null
        publishState()
    }

    /**
     * Applies the edit and persists it. The edit dialog is the only thing that mutates a location,
     * so committing here means the screen can never show unsaved changes.
     */
    fun onEditLocation(name: String, description: String) {
        activeLocation.name = name
        activeLocation.description = description
        commit()
    }

    /** Adds a feature of the chosen [FeatureType] to the active location. */
    fun onAddFeature(type: FeatureType, name: String, description: String) {
        val feature = when (type) {
            FeatureType.BASIC -> BasicFeature(name = name, description = description)
            FeatureType.STORE -> Store(name = name, description = description)
        }
        activeLocation.features.add(feature)
        commit()
    }

    fun onRemoveFeature(id: String) {
        activeLocation.features.removeAll { it.id == id }
        // Drop the selection too, or the store screen would keep showing a deleted store.
        if (activeStoreId == id) activeStoreId = null
        commit()
    }

    /** Points the store screen at one of the active location's stores. */
    fun onOpenStore(id: String) {
        activeStoreId = id
        _purchaseMessage.value = null
        publishState()
    }

    /**
     * Sells one of the stock entry at [entryIndex] to the character at [buyerIndex].
     *
     * The repository persists the buyer; the store it decremented lives in the active location, so
     * a successful sale commits that too. A failed one changed nothing, so it only republishes.
     */
    fun onBuy(entryIndex: Int, buyerIndex: Int) {
        val store = activeStore() ?: return
        val entry = store.stock.getOrNull(entryIndex) ?: return
        val buyer = characterRepo.characters.getOrNull(buyerIndex) ?: return

        val price = entry.price
        val result = characterRepo.purchaseFromStore(store, entry, buyer)
        _purchaseMessage.value = when (result) {
            is PurchaseResult.Success ->
                "${buyer.name} bought ${result.item.name} for $price caps."
            is PurchaseResult.InsufficientCaps ->
                "${buyer.name} has ${result.available} caps but needs ${result.required}."
            PurchaseResult.OutOfStock ->
                "${entry.template.name} is out of stock."
        }
        if (result is PurchaseResult.Success) commit() else publishState()
    }

    /** Adds a line of stock to the open store, at the quantity and price the GM entered. */
    fun onAddStock(templateId: Int, quantity: Int, price: Int) {
        val store = activeStore() ?: return
        store.stock.add(
            StockEntry(
                template = ItemDataSource.getItemTemplateById(templateId),
                quantity = quantity,
                price = price
            )
        )
        commit()
    }

    /** Restocks or re-prices an entry the open store already carries. */
    fun onEditStock(entryIndex: Int, quantity: Int, price: Int) {
        val entry = activeStore()?.stock?.getOrNull(entryIndex) ?: return
        entry.quantity = quantity
        entry.price = price
        commit()
    }

    fun onRemoveStock(entryIndex: Int) {
        val store = activeStore() ?: return
        if (entryIndex !in store.stock.indices) return
        store.stock.removeAt(entryIndex)
        commit()
    }

    fun onDismissPurchaseMessage() {
        _purchaseMessage.value = null
    }

    /** Moves the players to [location] and switches the GM's view to it as well. */
    fun onSetCurrentCampaignLocation(location: Location) {
        campaignRepo.setCurrentLocation(location)
        _currentLocationId.value = location.id
        publishState()
    }

    fun loadSavedLocations() {
        scope.launch { _savedLocations.update { repo.loadAllLocations() } }
    }

    private fun activeStore(): Store? = activeLocation.features
        .filterIsInstance<Store>()
        .firstOrNull { it.id == activeStoreId }

    private fun buildState() = LocationUiState.from(activeLocation)

    private fun buildStoreState() = activeStore()?.let { StoreUiState.from(it) }

    // Caps live in the character's inventory as a stackable, so each buyer's spending money has to
    // be totalled up the same way a purchase does.
    private fun buildBuyers() = characterRepo.characters.map {
        BuyerUiState(name = it.name, caps = it.countStackable(ItemDataSource.ID_CAPS))
    }

    private fun publishState() {
        scope.launch {
            _activeLocationState.update { buildState() }
            _activeStoreState.update { buildStoreState() }
            // Republished alongside the rest: a purchase changes the buyer's caps.
            _buyers.update { buildBuyers() }
        }
    }

    /**
     * Republishes the active location and writes it through the repository. Every mutation goes
     * through here so a change can never be shown without being persisted.
     */
    private fun commit() {
        publishState()
        scope.launch {
            repo.saveLocation(activeLocation)
            // Refresh in the same coroutine so the list can't be re-read before the write commits.
            _savedLocations.update { repo.loadAllLocations() }
        }
    }
}
