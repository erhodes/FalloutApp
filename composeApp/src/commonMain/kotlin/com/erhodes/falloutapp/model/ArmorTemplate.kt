package com.erhodes.falloutapp.model

import kotlinx.serialization.Serializable

//@Serializable(with = ArmorTemplateSerializer::class)
class ArmorTemplate(
    name: String,
    description: String,
    load: Int,
    tier: Int,
    id: Int,
    val durability: Int,
    val toughness: Int
    ): ItemTemplate(name,  description, load, tier, id) {

}