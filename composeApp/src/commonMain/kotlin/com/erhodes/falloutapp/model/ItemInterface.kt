package com.erhodes.falloutapp.model

interface ItemInterface {
    /**
     * What item is this an instance of
     */
    val template: ItemTemplate
    val name: String
        get() = template.name
    val load: Int
        get() = template.load
}