package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.ItemTemplate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ItemTemplateSerializer: KSerializer<ItemTemplate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ItemTemplate", PrimitiveKind.INT)

    override fun serialize(
        encoder: Encoder,
        value: ItemTemplate
    ) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): ItemTemplate {
        return ItemDataSource.getItemTemplateById(decoder.decodeInt())
    }

}