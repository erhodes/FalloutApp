package com.erhodes.falloutapp.model

class StackableItemTemplate(
    name: String,
    description: String,
    load: Int,
    tier: Int,
    id: Int,
    val max: Int
) : ItemTemplate(name, description, load, tier, id)