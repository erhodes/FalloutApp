package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.model.ability.Ability
import kotlinx.serialization.Serializable

//@Serializable(with = ArmorTemplateSerializer::class)
class ArmorTemplate(
    name: String,
    description: String,
    load: Int,
    tier: Int,
    id: Int,
    val durability: Int,
    val toughness: Int,
    val abilities: List<Ability> = listOf()
    ): ItemTemplate(name,  description, load, tier, id) {

}