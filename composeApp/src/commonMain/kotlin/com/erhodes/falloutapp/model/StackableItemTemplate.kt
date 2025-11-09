package com.erhodes.falloutapp.model

class StackableItemTemplate(
    name: String,
    load: Int,
    id: Int,
    override val max: Int
) : ItemTemplate(name, load, id), StackableTemplate {
}