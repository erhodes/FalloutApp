package com.erhodes.falloutapp.model

open class Item(
    /**
     * What item is this an instance of
     */
    val template: ItemTemplate
) {
    val name = template.name
    val load = template.load
}