package com.erhodes.falloutapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Weapon")
class Weapon(override val template: ItemTemplate, var ammo: Int): Item {
    val damage: List<Int> = (template as WeaponTemplate).damage
    val ability: List<String> = (template as WeaponTemplate).ability
    val magazineSize: Int = (template as WeaponTemplate).magazineSize
    val range: Int = (template as WeaponTemplate).range
}