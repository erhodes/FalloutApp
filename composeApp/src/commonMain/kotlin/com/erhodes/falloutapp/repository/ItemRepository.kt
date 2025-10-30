package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.ItemTemplate

class ItemRepository {
    val availableItems = ArrayList<ItemTemplate>()

    companion object {
        val BANNER = ItemTemplate(
            "Banner", 1
        )
        val LEATHER_ARMOR = ArmorTemplate(
            "Leather",
            2,
            3, 0
        )
    }

    init {
        // some sample items for now
        availableItems.add(
            LEATHER_ARMOR
        )
        availableItems.add(
            BANNER
        )
    }
}