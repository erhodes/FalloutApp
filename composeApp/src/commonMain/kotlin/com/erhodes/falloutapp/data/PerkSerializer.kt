package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Perk
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object PerkSerializer: KSerializer<Perk> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Perk", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Perk) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): Perk {
        return PerkDataSource.getPerkById(decoder.decodeInt())
    }
}