package com.erhodes.falloutapp.model

import kotlinx.serialization.Serializable

//@Serializable
class Armor(val base: ArmorTemplate): Item(base) {
    var damageTaken = 0
//    val durability = base.durability
//    val toughness = base.toughness
}