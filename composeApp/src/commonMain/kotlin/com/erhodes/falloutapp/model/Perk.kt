package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.data.PerkSerializer
import com.erhodes.falloutapp.model.effect.Effect
import kotlinx.serialization.Serializable

@Serializable(with = PerkSerializer::class)
class Perk(
    val name: String,
    val description: String,
    val id: Int,
    val effect: Effect? = null,
    vararg val requirements: Requirement
) {
}