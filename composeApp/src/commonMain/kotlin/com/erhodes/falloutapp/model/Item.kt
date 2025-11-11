package com.erhodes.falloutapp.model

import kotlinx.serialization.Serializable

interface Item {
    /**
     * What item is this an instance of
     */
    val template: ItemTemplate
    val name: String
        get() = template.name
    val description: String
        get() = template.description
    val load: Int
        get() = template.load
}