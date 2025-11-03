package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.data.PerkSerializer
import kotlinx.serialization.Serializable

@Serializable(with = PerkSerializer::class)
class Perk(
    val id: Int,
    val name: String,
    val description: String
) {
}