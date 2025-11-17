package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.data.PerkSerializer
import kotlinx.serialization.Serializable

@Serializable(with = PerkSerializer::class)
class Perk(
    val name: String,
    val description: String,
    val id: Int,
    vararg val requirements: Requirement
) {
}