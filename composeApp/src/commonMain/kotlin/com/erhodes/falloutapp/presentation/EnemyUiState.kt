package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.Character

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
) {
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
        )
    }
}
