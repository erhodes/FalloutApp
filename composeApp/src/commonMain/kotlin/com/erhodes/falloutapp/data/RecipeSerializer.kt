package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Recipe
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RecipeSerializer : KSerializer<Recipe> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Recipe", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Recipe) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): Recipe {
        return RecipeDataSource.getRecipeById(decoder.decodeInt())
    }
}
