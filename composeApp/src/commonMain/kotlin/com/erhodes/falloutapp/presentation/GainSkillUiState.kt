package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.PlayerCharacter

data class GainSkillUiState(
    val character: PlayerCharacter,
    val bonuses: Int,
    val milestone: Boolean,
    val appliedBonuses: ArrayList<Int> = arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0)
) {
}
