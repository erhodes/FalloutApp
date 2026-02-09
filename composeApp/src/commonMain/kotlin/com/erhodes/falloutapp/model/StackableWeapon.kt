package com.erhodes.falloutapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("StackableWeapon")
class StackableWeapon(template: ItemTemplate, ammo: Int): Weapon(template, ammo) {

}