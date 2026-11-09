package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.campaign.BasicFeature
import com.erhodes.falloutapp.model.campaign.Feature
import com.erhodes.falloutapp.model.campaign.Store

/**
 * Mirrors the [Feature] hierarchy the way [LocationUiState] mirrors
 * [com.erhodes.falloutapp.model.campaign.Location], so a screen only sees the fields it displays.
 * Subclasses that grow their own fields get their own state class here.
 */
sealed interface FeatureUiState {
    val id: String
    val name: String
    val description: String

    /** Which subclass this came from, so a row can label itself without a `when`. */
    val type: FeatureType

    companion object {
        fun from(feature: Feature): FeatureUiState = when (feature) {
            is Store -> StoreUiState.from(feature)
            is BasicFeature -> BasicFeatureUiState(
                id = feature.id,
                name = feature.name,
                description = feature.description
            )
        }
    }
}

/**
 * The [Feature] subclasses that can be created from the UI. The screen offers these as choices and
 * the ViewModel maps the chosen one back to a model class, so adding a subclass means adding an
 * entry here rather than teaching the screen about the model.
 */
enum class FeatureType(val label: String) {
    BASIC("Feature"),
    STORE("Store"),
}

data class BasicFeatureUiState(
    override val id: String,
    override val name: String,
    override val description: String,
) : FeatureUiState {
    override val type = FeatureType.BASIC
}

data class StoreUiState(
    override val id: String,
    override val name: String,
    override val description: String,
    val stock: List<StockEntryUiState> = emptyList(),
) : FeatureUiState {
    override val type = FeatureType.STORE

    companion object {
        fun from(store: Store): StoreUiState = StoreUiState(
            id = store.id,
            name = store.name,
            description = store.description,
            stock = store.stock.map {
                StockEntryUiState(
                    templateId = it.template.id,
                    name = it.template.name,
                    quantity = it.quantity,
                    price = it.price
                )
            }
        )
    }
}

/**
 * One line of a store's stock, flattened for display.
 *
 * A [com.erhodes.falloutapp.model.campaign.StockEntry] has no id of its own, so the screen addresses
 * an entry by its position in [StoreUiState.stock] - the same index-as-handle convention
 * `EncounterScreen` uses for enemies. The indices line up with the model list because this state is
 * rebuilt from it after every mutation.
 */
data class StockEntryUiState(
    val templateId: Int,
    val name: String,
    val quantity: Int,
    val price: Int,
)

/**
 * A character who can buy from a store, addressed by position the same way stock entries are.
 * [caps] is what they currently hold, so the screen can disable a purchase it knows will fail.
 */
data class BuyerUiState(
    val name: String,
    val caps: Int,
)
