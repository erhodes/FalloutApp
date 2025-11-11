package com.erhodes.falloutapp.model

class StackableItemTemplate(
    name: String,
    description: String,
    load: Int,
    id: Int,
    override val max: Int
) : ItemTemplate(name, description, load, id), StackableTemplate {
}