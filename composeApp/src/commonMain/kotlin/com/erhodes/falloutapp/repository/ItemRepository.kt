package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.StackableItem
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

        const val ID_RAIDER_PA = 7
        val RAIDER_POWER_ARMOR = ArmorTemplate(
            name = "Raider Power Armor",
            description = "A very poorly maintained suit of power armor. Beneath all the rusted metal, the frame still works.",
            load = 5,
            id = ID_RAIDER_PA,
            durability = 4,
            toughness = 1
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
            itemList[ID_RAIDER_PA] = RAIDER_POWER_ARMOR
            itemList[8] = StackableItemTemplate(
                name = "Parts",
                description = "An assortment of spare parts, suitable for making repairs and crafting gadgets.",
                load = 1,
                id = 8,
                max = 10
            )
            itemList[9] = StackableItemTemplate(
                name = "Medical Supplies",
                description = "An assortment of medical supplies, suitable for treating injuries and crafting chems.",
                load = 1,
                id = 9,
                max = 10
            )
            itemList[10] = ItemTemplate(
                name = "Stimpak",
                description = "A medicinal chem. Heals 2 damage. Can target self or an adjacent ally.",
                load = 1,
                id = 10
            )
            itemList[11] = ItemTemplate(
                name = "Super Stimpak",
                description = "An advanced healing chem. Heals all damage suffered, but inflicts a point of Fatigue. Can target self or an adjacent ally.",
                load = 1,
                id = 11
            )
            itemList[12] = ItemTemplate(
                name = "Buffout",
                description = "Grants Advantage on all Strength, Endurance, and Agility checks, but you cannot Push. Lasts 30 minutes. Suffer 1 Fatigue.",
                load = 1,
                id = 12
            )
            itemList[13] = ItemTemplate(
                name = "Jet",
                description = "Gain an additional AP every turn for 1 minute. You cannot use Push. Suffer 2 Fatigue.",
                load = 1,
                id = 13
            )
            itemList[14] = ItemTemplate(
                name = "Med-X",
                description = "Ignore the Bloodied condition for 1 minute. Suffer 1 Fatigue.",
                load = 1,
                id = 14
            )
            itemList[15] = ItemTemplate(
                name = "Mentats",
                description = "Grants Advantage on all Perception, Charisma, and Intelligence checks, but you cannot Push. Lasts 30 minutes. Suffer 1 Fatigue.",
                load = 1,
                id = 15
            )
            itemList[16] = ItemTemplate(
                name = "Nuka-Cola",
                description = "Clear 1 Stress.",
                load = 1,
                id = 16
            )
            itemList[17] = ItemTemplate(
                name = "Psycho",
                description = "Gain Advantage on melee attacks, and ignore the effects of Bloodied and Suppressed. You cannot use Push. Lasts 5 minutes. Suffer 2 Fatigue.",
                load = 1,
                id = 17
            )
            itemList[18] = ItemTemplate(
                name = "Radaway",
                description = "Clear 10 Radiation. Usage requires an Activity performed by a character with at least 5 Medicine.",
                load = 1,
                id = 18
            )
            itemList[19] = ItemTemplate(
                name = "Rax-X",
                description = "Lower Radiation damage by 10. Lasts one hour.",
                load = 1,
                id = 19
            )
            itemList[20] = WeaponTemplate(
                name = "Hunting Rifle",
                description = "A bolt-action rifle designed for hunting game animals.",
                load = 2,
                id = 20,
                damage = listOf(1, 2, 3),
                ability = listOf("", "Shred 2", "Shred 4"),
                passive = "Accurate",
                range = 20,
                magazineSize = 3
            )
            itemList[21] = WeaponTemplate(
                name = "Shotgun",
                description = "A Winchester Widowmaker double-barreled 12 gauge shotgun. A short barrel, with mahogany grip.",
                load = 2,
                id = 20,
                damage = listOf(1, 2, 3),
                ability = listOf("", "Knockback 1", "Knockback 2"),
                passive = "Storm",
                range = 10,
                magazineSize = 3
            )
        }

        fun getItemTemplateById(id: Int): ItemTemplate {
            return itemList[id]!!
        }
    }
}