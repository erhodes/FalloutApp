package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.repository.ItemRepository
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ArmorTemplateSerializer: KSerializer<ArmorTemplate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ArmorTemplate", PrimitiveKind.INT)

    override fun serialize(
        encoder: Encoder,
        value: ArmorTemplate
    ) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): ArmorTemplate {
        return ItemRepository.getItemTemplateById(decoder.decodeInt()) as ArmorTemplate
    }
}