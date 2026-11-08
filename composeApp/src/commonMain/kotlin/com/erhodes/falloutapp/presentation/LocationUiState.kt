package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.campaign.BasicLocation
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement

/** Mirrors the [Location] hierarchy so each screen only sees the fields it displays. */
sealed interface LocationUiState {
    val id: String
    val name: String
    val description: String
    val features: List<FeatureUiState>

    companion object {
        val EMPTY = BasicLocationUiState(id = "", name = "", description = "")

        fun from(location: Location): LocationUiState = when (location) {
            is Settlement -> SettlementUiState(
                id = location.id,
                name = location.name,
                description = location.description,
                population = location.population,
                food = location.food,
                features = location.features.map { FeatureUiState.from(it) }
            )
            is BasicLocation -> BasicLocationUiState(
                id = location.id,
                name = location.name,
                description = location.description,
                features = location.features.map { FeatureUiState.from(it) }
            )
        }
    }
}

data class BasicLocationUiState(
    override val id: String,
    override val name: String,
    override val description: String,
    override val features: List<FeatureUiState> = emptyList(),
) : LocationUiState

data class SettlementUiState(
    override val id: String,
    override val name: String,
    override val description: String,
    val population: Int,
    val food: Int,
    override val features: List<FeatureUiState> = emptyList(),
) : LocationUiState
