package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.model.ability.Ability
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("Weapon")
open class Weapon(override val template: ItemTemplate, var ammo: Int): Item {
    val damage: List<Int> = (template as WeaponTemplate).damage

    @Transient
    val passive: List<Ability> = (template as WeaponTemplate).passive
    @Transient
    val ability: List<Ability> = (template as WeaponTemplate).ability
    val magazineSize: Int = (template as WeaponTemplate).magazineSize
    val range: Int = (template as WeaponTemplate).range

    //((count + max -1) / max)
    override val load: Int
        get() = if (magazineSize > 0) (ammo + magazineSize - 1)/ magazineSize else (template.load)
}