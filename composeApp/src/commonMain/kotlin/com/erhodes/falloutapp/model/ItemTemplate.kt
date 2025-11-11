package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.data.ItemTemplateSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ItemTemplateSerializer::class)
open class ItemTemplate(
    val name: String,
    val description: String,
    val load: Int,
    val id: Int
) {
}