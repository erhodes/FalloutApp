package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.Enemy

class EnemyUiState(
    val index: Int,
    val character: Enemy
) {

    companion object {
        fun from(index: Int, character: Enemy) = EnemyUiState(
            index = index,
            character = character
        )
    }
}
