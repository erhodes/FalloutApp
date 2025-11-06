package com.erhodes.falloutapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Armor")
/**
 * Always use an ArmorTemplate. The param is ItemTemplate due to serialization issues I haven't cracked yet
 */
class Armor(override val template: ItemTemplate, var damageTaken: Int): Item {
    val durability = (template as? ArmorTemplate)?.durability ?: 0
    val toughness = (template as? ArmorTemplate)?.toughness ?: 0
}