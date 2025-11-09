package com.erhodes.falloutapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Needs a StackableItemTemplate
 */
@Serializable
@SerialName("Stackable")
class StackableItem(
    override val template: ItemTemplate,
    var count: Int
) : Item {
    val max = (template as StackableItemTemplate).max
    override val load: Int
        get() = ((count + max -1) / max)
}