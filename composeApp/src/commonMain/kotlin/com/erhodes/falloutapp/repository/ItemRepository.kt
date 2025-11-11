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
            "Treat your CHA as 5 points higher when determining the range of abilities.",
            1,
            ID_BANNER
        )
        const val ID_LEATHER_ARMOR = 1
        val LEATHER_ARMOR = ArmorTemplate(
            "Leather",
            "Layers of boiled leather provide adequate protection.",
            2,
            id = ID_LEATHER_ARMOR,
            3,
            0
        )
        const val ID_ASSAULT_RIFLE = 2
        val ASSAULT_RIFLE = WeaponTemplate(
            name = "Assault Rifle",
            description = "An old AK-47. Its famed reliability ensures it continues to spew bullets decades after the end of civilization.",
            load = 2,
            id = 2,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Burst",
            range = 15,
            magazineSize = 6
        )

        const val ID_CAPS = 5
        val CAPS = StackableItemTemplate(
            name = "Caps",
            description = "The lifeblood of the post-nuclear economy.",
            load = 1,
            id = 5,
            max = 10
        )

        init {
            itemList[ID_BANNER] = BANNER
            itemList[ID_LEATHER_ARMOR] = LEATHER_ARMOR
            itemList[ID_ASSAULT_RIFLE] = ASSAULT_RIFLE
            itemList[3] = WeaponTemplate(
                name = "Sledgehammer",
                "Equally effective at smashing walls and skulls",
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
                description = "A MK2 fragmentation-type anti-personnel hand grenade.",
                load = 1,
                id = 4,
                damage = listOf(1, 2, 3),
                ability = listOf("", "Suppress 2", "Suppress 3"),
                passive = "Blast 1, Consumable",
                range = 8,
                magazineSize = 0
            )
            itemList[ID_CAPS] = CAPS
            itemList[6] = WeaponTemplate(
                name = "Laser Rifle",
                description = "A worn out AER9 Laser Rifle. Smells like burning ozone when fired",
                load = 2,
                id = 6,
                damage = listOf(1, 2, 3),
                ability = listOf("", "Burn 2", "Burn 4"),
                passive = "Burst",
                range = 15,
                magazineSize = 6
            )

        }

        fun getItemTemplateById(id: Int): ItemTemplate {
            return itemList[id]!!
        }
    }
}