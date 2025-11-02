package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.Character

data class BonusSkillUiState(
    val character: Character,
    val bonuses: Int,
    val appliedBonuses: ArrayList<Int> = arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0)
) {
}