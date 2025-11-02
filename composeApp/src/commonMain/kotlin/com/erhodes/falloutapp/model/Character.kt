package com.erhodes.falloutapp.model

import kotlinx.serialization.Serializable

@Serializable
class Character(
    val name: String,
    val strength: Int = 1,
    val perception: Int = 1,
    val endurance: Int = 1,
    val charisma: Int = 1,
    val agility: Int = 1,
    val intelligence: Int = 1,
    val luck: Int = 1,
) {

    var load = 0
    val loadoutLimit = strength + 4

    var inventoryWeight = 0
    var inventoryLimit = 10

    val loadout = ArrayList<Item>()
    val inventory = ArrayList<Item>()

    fun addItemToInventory(item: Item) {
        inventory.add(item)
        inventoryWeight += item.load
    }

    fun removeItemFromInventory(item: Item) {
        inventory.remove(item)
        inventoryWeight -= item.load
    }

    /**
     * Equip an item from this character's inventory
     */
    fun equipItem(item: Item) {
        if (!inventory.contains(item)) return
        if (item.load + load > loadoutLimit) return

        load += item.load
        loadout.add(item)
        removeItemFromInventory(item)
    }

    fun unequipItem(item: Item) {
        load -= item.load
        loadout.remove(item)
        addItemToInventory(item)
    }
}