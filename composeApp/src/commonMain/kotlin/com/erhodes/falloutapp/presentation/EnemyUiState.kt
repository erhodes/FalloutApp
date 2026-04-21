package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.action.Action

data class EnemyUiState(
    val index: Int,
    val name: String,
    val stats: List<Int>,
    val skills: List<Int>,
    val damageTaken: Int,
    val isBloodied: Boolean,
    val armorToughness: Int,
    val armorDamage: Int,
    val armorDurability: Int,
    val actions: List<Action> = emptyList(),
) {
    fun testValue(action: Action): Int {
        val stat = action.stat ?: return 0
        val skill = action.skill ?: return 0
        return stats[stat.ordinal] + skills[skill.ordinal]
    }

    companion object {
        fun from(index: Int, character: Character) = EnemyUiState(
            index = index,
            name = character.name,
            stats = (0..6).map { character.getStatByOrdinal(it) },
            skills = character.skills.toList(),
            damageTaken = character.damageTaken,
            isBloodied = character.isBloodied(),
            armorToughness = character.getArmorToughness(),
            armorDamage = character.getArmorDamage(),
            armorDurability = character.getArmorDurability(),
            actions = character.actions.toList(),
        )
    }
}
