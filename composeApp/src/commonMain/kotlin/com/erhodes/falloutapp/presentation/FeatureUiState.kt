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
            is Store -> StoreUiState(
                id = feature.id,
                name = feature.name,
                description = feature.description
            )
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
) : FeatureUiState {
    override val type = FeatureType.STORE
}
