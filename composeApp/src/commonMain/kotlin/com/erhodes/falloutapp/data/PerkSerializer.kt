package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.repository.ItemRepository
import com.erhodes.falloutapp.repository.PerkRepository
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
        return PerkRepository.getPerkById(decoder.decodeInt())
    }
}