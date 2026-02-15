package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.model.ability.Ability

open class WeaponTemplate(
    name: String,
    description: String,
    load: Int,
    tier: Int,
    id: Int,
    val damage: List<Int>,
    val ability: List<Ability>,
    val passive: List<Ability>,
    val magazineSize: Int,
    val range: Int
): ItemTemplate(name, description, load, tier, id)