package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.Character

class EnemyUiState(
    val index: Int,
    val character: Character
) {

    companion object {
        fun from(index: Int, character: Character) = EnemyUiState(
            index = index,
            character = character
        )
    }
}
