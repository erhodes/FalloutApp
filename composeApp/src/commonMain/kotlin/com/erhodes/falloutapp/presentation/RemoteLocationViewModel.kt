package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.campaign.Store
import com.erhodes.falloutapp.network.StorePurchaseOutcome
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.LoginRepository
import com.erhodes.falloutapp.repository.RemoteLocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoteLocationViewModel: ViewModel(), KoinComponent {
    private val repo: RemoteLocationRepository by inject()
    private val loginRepository: LoginRepository by inject()
    private val characterRepository: CharacterRepository by inject()

    val activeLocationState = repo.activeLocation
        .map { LocationUiState.from(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LocationUiState.EMPTY)

    /** Which store the player has opened, if any. */
    private val _activeStoreId = MutableStateFlow<String?>(null)

    /**
     * The opened store, rebuilt whenever the cached location changes - which is how a purchase shows
     * up, since the server sends back the location it just decremented.
     */
    val activeStoreState = combine(repo.activeLocation, _activeStoreId) { location, storeId ->
        location.features
            .filterIsInstance<Store>()
            .firstOrNull { it.id == storeId }
            ?.let { StoreUiState.from(it) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    /**
     * The player's own character, as the only buyer. Republished after a purchase so the caps shown
     * follow the spend.
     */
    private val _buyers = MutableStateFlow(buildBuyers())
    val buyers = _buyers.asStateFlow()

    private val _purchaseMessage = MutableStateFlow<String?>(null)
    val purchaseMessage = _purchaseMessage.asStateFlow()

    fun onOpenStore(id: String) {
        _activeStoreId.value = id
        _purchaseMessage.value = null
        _buyers.value = buildBuyers()
    }

    /**
     * Asks the server to sell the player the entry at [entryIndex].
     *
     * The buyer parameter the screen passes is ignored: a player buys with their own character, and
     * the index only exists because the screen is shared with the GM's version.
     */
    fun onBuy(entryIndex: Int) {
        val storeId = _activeStoreId.value ?: return
        val character = characterRepository.activeCharacter
        viewModelScope.launch {
            val response = loginRepository.purchaseFromStore(storeId, entryIndex, character)
            _purchaseMessage.value = response.message
            // The caps total only moves on a sale, but republishing regardless keeps the displayed
            // total honest if the character changed elsewhere while the store was open.
            _buyers.value = buildBuyers()
            if (response.outcome != StorePurchaseOutcome.SUCCESS) {
                // Stock the server rejected us over may have moved on; refresh so the screen agrees.
                // A refresh that itself fails shouldn't bury the message explaining the refusal.
                runCatching { loginRepository.getActiveLocation() }
            }
        }
    }

    fun onDismissPurchaseMessage() {
        _purchaseMessage.value = null
    }

    private fun buildBuyers(): List<BuyerUiState> {
        val character = characterRepository.activeCharacter
        return listOf(
            BuyerUiState(
                name = character.name,
                caps = character.countStackable(ItemDataSource.ID_CAPS)
            )
        )
    }
}
