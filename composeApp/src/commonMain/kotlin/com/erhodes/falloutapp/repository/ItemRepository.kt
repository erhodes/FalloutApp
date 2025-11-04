package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.ItemTemplate

class ItemRepository {
    companion object {
        val itemList = HashMap<Int, ItemTemplate>()
        const val ID_BANNER = 0
        val BANNER = ItemTemplate(
            "Banner",
            1,
            ID_BANNER
        )
        const val ID_LEATHER_ARMOR = 1
        val LEATHER_ARMOR = ArmorTemplate(
            "Leather",
            2,
            id = ID_LEATHER_ARMOR,
            3,
            0
        )

        init {
            itemList[ID_BANNER] = BANNER
            itemList[ID_LEATHER_ARMOR] = LEATHER_ARMOR
        }

        fun getItemTemplateById(id: Int): ItemTemplate {
            return itemList[id]!!
        }
    }
}