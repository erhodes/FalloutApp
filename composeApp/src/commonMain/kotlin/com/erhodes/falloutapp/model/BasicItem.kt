package com.erhodes.falloutapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("BasicItem")
class BasicItem(
    /**
     * What item is this an instance of
     */
    override val template: ItemTemplate
): Item {

}