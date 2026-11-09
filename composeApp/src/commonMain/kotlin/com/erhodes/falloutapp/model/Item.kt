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

/**
 * Builds the instance type that matches this template. The starting values live here so that
 * everything handing a character a new item - granting one, crafting, buying from a store - agrees
 * on what a fresh item looks like.
 */
fun ItemTemplate.createItem(): Item = when (this) {
    is ArmorTemplate -> Armor(this, 0)
    is WeaponTemplate -> Weapon(this, 0)
    is StackableItemTemplate -> StackableItem(this, 1)
    else -> BasicItem(this)
}