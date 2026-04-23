package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Trait
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object TraitSerializer: KSerializer<Trait> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Trait", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Trait) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): Trait {
        return TraitDataSource.getTraitById(decoder.decodeInt())
    }
}
