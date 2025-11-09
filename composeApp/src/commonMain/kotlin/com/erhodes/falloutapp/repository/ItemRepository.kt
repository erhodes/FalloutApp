package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.StackableItemTemplate
import com.erhodes.falloutapp.model.WeaponTemplate

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
            itemList[2] = WeaponTemplate(
                name = "Assault Rifle",
                load = 2,
                id = 2,
                damage = listOf(1, 2, 3),
                ability = listOf("", "Suppress 2", "Suppress 3"),
                passive = "Burst",
                range = 15,
                magazineSize = 6
            )
            itemList[3] = WeaponTemplate(
                name = "Sledgehammer",
                load = 2,
                id = 3,
                damage = listOf(1, 2, 3),
                ability = listOf("Knockback 1", "Knockback 2", "Knockback 3"),
                passive = "Windup",
                range = 1,
                magazineSize = 0
            )
            itemList[4] = WeaponTemplate(
                name = "Frag Grenade",
                load = 1,
                id = 4,
                damage = listOf(1, 2, 3),
                ability = listOf("", "Suppress 2", "Suppress 3"),
                passive = "Blast 1, Consumable",
                range = 8,
                magazineSize = 0
            )
            itemList[5] = StackableItemTemplate(
                name = "Caps",
                load = 1,
                id = 5,
                max = 10
            )

        }

        fun getItemTemplateById(id: Int): ItemTemplate {
            return itemList[id]!!
        }
    }
}