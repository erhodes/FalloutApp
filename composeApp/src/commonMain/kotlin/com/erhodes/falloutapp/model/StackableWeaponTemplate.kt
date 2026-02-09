package com.erhodes.falloutapp.model

class StackableWeaponTemplate(
    name: String,
    description: String,
    load: Int,
    tier: Int,
    id: Int,
    damage: List<Int>,
    ability: List<String>,
    passive: String,
    magazineSize: Int,
    range: Int,
    override val max: Int
): WeaponTemplate(name, description, load, tier, id, damage, ability, passive, magazineSize, range), StackableTemplate