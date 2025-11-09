package com.erhodes.falloutapp.model

import kotlinx.serialization.Serializable

//@Serializable(with = ArmorTemplateSerializer::class)
class ArmorTemplate(
    name: String,
    load: Int,
    id: Int,
    val durability: Int,
    val toughness: Int
    ): ItemTemplate(name, load, id) {

}