package com.erhodes.falloutapp.model

class WeaponTemplate(
    name: String,
    load: Int,
    id: Int,
    val damage: List<Int>,
    val ability: List<String>,
    val passive: String,
    val magazineSize: Int,
    val range: Int
): ItemTemplate(name, load, id) {
}