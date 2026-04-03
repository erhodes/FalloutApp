package com.erhodes.falloutapp.model.condition

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ConditionTemplateSerializer: KSerializer<ConditionTemplate> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ItemTemplate", PrimitiveKind.INT)

    override fun serialize(
        encoder: Encoder,
        value: ConditionTemplate
    ) {
        encoder.encodeInt(value.ordinal)
    }

    override fun deserialize(decoder: Decoder): ConditionTemplate {
        return ConditionTemplate.entries[decoder.decodeInt()]
    }
}