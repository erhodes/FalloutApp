package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement

/** Mirrors the [Location] hierarchy so each screen only sees the fields it displays. */
sealed interface LocationUiState {
    val id: Int
    val name: String
    val description: String

    companion object {
        val EMPTY = BasicLocationUiState(id = -1, name = "", description = "")

        fun from(location: Location): LocationUiState = when (location) {
            is Settlement -> SettlementUiState(
                id = location.id,
                name = location.name,
                description = location.description,
                population = location.population,
                food = location.food
            )
            else -> BasicLocationUiState(
                id = location.id,
                name = location.name,
                description = location.description
            )
        }
    }
}

data class BasicLocationUiState(
    override val id: Int,
    override val name: String,
    override val description: String,
) : LocationUiState

data class SettlementUiState(
    override val id: Int,
    override val name: String,
    override val description: String,
    val population: Int,
    val food: Int,
) : LocationUiState
