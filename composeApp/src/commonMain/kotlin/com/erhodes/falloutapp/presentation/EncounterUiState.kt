package com.erhodes.falloutapp.presentation

data class EncounterUiState(
    val name: String,
    val enemies: List<EnemyUiState>,
) {
    companion object {
        val EMPTY = EncounterUiState(name = "", enemies = emptyList())
    }
}
