package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.data.TraitSerializer
import com.erhodes.falloutapp.model.effect.Effect
import kotlinx.serialization.Serializable

@Serializable(with = TraitSerializer::class)
class Trait(
    val name: String,
    val description: String,
    val id: Int,
    val effect: Effect? = null,
) {
}
