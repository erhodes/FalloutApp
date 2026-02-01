package com.erhodes.falloutapp.model

class WeaponTemplate(
    name: String,
    description: String,
    load: Int,
    tier: Int,
    id: Int,
    val damage: List<Int>,
    val ability: List<String>,
    val passive: String,
    val magazineSize: Int,
    val range: Int
): ItemTemplate(name, description, load, tier, id) {
}