package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.StackableItemTemplate
import com.erhodes.falloutapp.model.WeaponTemplate

object ItemDataSource {

    val itemMap = HashMap<Int, ItemTemplate>()
    fun getItemTemplateById(id: Int): ItemTemplate {
        return itemMap[id]!!
    }

    // for testing purposes, a few template IDs are listed here
    const val ID_ARMOR_LEATHER = 1000
    const val ID_PA_RAIDER = 1001
    const val ID_BATTLE_STANDARD = 3000
    const val ID_ASSAULT_RIFLE = 0
    const val ID_RATIONS = 1996
    const val ID_CAPS = 1997
    init {
        // weapons
        itemMap[0] = WeaponTemplate(
            name = "Assault Rifle",
            description = "An old AK-47. Its famed reliability ensures it continues to spew bullets decades after the end of civilization.",
            load = 2,
            id = 0,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Burst",
            range = 15,
            magazineSize = 6
        )
        itemMap[1] = WeaponTemplate(
            name = "Frag Grenade",
            description = "A MK2 fragmentation-type anti-personnel hand grenade.",
            load = 1,
            id = 1,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Blast 1, Consumable",
            range = 8,
            magazineSize = 0
        )
        itemMap[2] = WeaponTemplate(
            name = "Hunting Rifle",
            description = "A bolt-action rifle designed for hunting game animals.",
            load = 2,
            id = 2,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Shred 2", "Shred 4"),
            passive = "Accurate",
            range = 20,
            magazineSize = 3
        )
        itemMap[3] = WeaponTemplate(
            name = "Laser Pistol",
            description = "A worn out AEP7 Laser Pistol. Smells like burning ozone when fired",
            load = 1,
            id = 3,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Ignite 2", "Ignite 4"),
            passive = "",
            range = 10,
            magazineSize = 3
        )
        itemMap[4] = WeaponTemplate(
            name = "Laser Rifle",
            description = "A worn out AER9 Laser Rifle. Smells like burning ozone when fired",
            load = 2,
            id = 4,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Ignite 2", "Ignite 4"),
            passive = "Burst",
            range = 15,
            magazineSize = 6
        )
        itemMap[5] = WeaponTemplate(
            name = "Pistol",
            description = "An N99 sideam. Lightweight and effective at close ranges.",
            load = 1,
            id = 5,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Maim 1", "Maim 2"),
            passive = "",
            range = 15,
            magazineSize = 3
        )
        itemMap[6] = WeaponTemplate(
            name = "Shotgun",
            description = "A Winchester Widowmaker double-barreled 12 gauge shotgun. A short barrel, with mahogany grip.",
            load = 2,
            id = 6,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Knockback 1", "Knockback 2"),
            passive = "Storm",
            range = 10,
            magazineSize = 3
        )
        itemMap[7] = WeaponTemplate(
            name = "Sledgehammer",
            "Equally effective at smashing walls and skulls",
            load = 2,
            id = 7,
            damage = listOf(1, 2, 3),
            ability = listOf("Knockback 1", "Knockback 2", "Knockback 3"),
            passive = "Windup",
            range = 1,
            magazineSize = 0
        )
        itemMap[8] = WeaponTemplate(
            name = "Mine",
            "An improvised proximity explosive.",
            load = 2,
            id = 7,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Blast 1, Trap 1, Consumable",
            range = 1,
            magazineSize = 0
        )

        // Armors
        itemMap[1000] = ArmorTemplate(
            "Leather",
            "Layers of boiled leather provide adequate protection.",
            2,
            id = 1000,
            3,
            0
        )
        itemMap[1001] = ArmorTemplate(
            name = "Raider Power Armor",
            description = "A very poorly maintained suit of power armor. Beneath all the rusted metal, the frame still works.",
            load = 5,
            id = 1001,
            durability = 4,
            toughness = 1
        )

        // Currencies
        itemMap[ID_RATIONS] = StackableItemTemplate(
            name = "Rations",
            description = "The lifeblood of the post-nuclear economy.",
            load = 1,
            id = ID_RATIONS,
            max = 10
        )
        itemMap[ID_CAPS] = StackableItemTemplate(
            name = "Caps",
            description = "The lifeblood of the post-nuclear economy.",
            load = 1,
            id = ID_CAPS,
            max = 10
        )
        itemMap[1998] = StackableItemTemplate(
            name = "Medical Supplies",
            description = "An assortment of medical supplies, suitable for treating injuries and crafting chems.",
            load = 1,
            id = 1998,
            max = 10
        )
        itemMap[1999] = StackableItemTemplate(
            name = "Parts",
            description = "An assortment of spare parts, suitable for making repairs and crafting gadgets.",
            load = 1,
            id = 1999,
            max = 10
        )

        // Chems

        itemMap[2000] = ItemTemplate(
            name = "Stimpak",
            description = "A medicinal chem. Heals 2 damage.",
            load = 1,
            id = 2000
        )
        itemMap[2001] = ItemTemplate(
            name = "Super Stimpak",
            description = "An advanced healing chem. Heals all damage suffered, but inflicts a point of Fatigue.",
            load = 1,
            id = 2001
        )
        itemMap[2002] = ItemTemplate(
            name = "Buffout",
            description = "Grants Advantage on all Strength, Endurance, and Agility checks, but you cannot Push. Lasts 30 minutes. Suffer 1 Fatigue.",
            load = 1,
            id = 2002
        )
        itemMap[2003] = ItemTemplate(
            name = "Jet",
            description = "Gain an additional AP every turn for 1 minute. You cannot use Push. Suffer 2 Fatigue.",
            load = 1,
            id = 2003
        )
        itemMap[2004] = ItemTemplate(
            name = "Med-X",
            description = "Ignore the Bloodied condition for 1 minute. Suffer 1 Fatigue.",
            load = 1,
            id = 2004
        )
        itemMap[2005] = ItemTemplate(
            name = "Mentats",
            description = "Grants Advantage on all Perception, Charisma, and Intelligence checks, but you cannot Push. Lasts 30 minutes. Suffer 1 Fatigue.",
            load = 1,
            id = 2005
        )
        itemMap[2006] = ItemTemplate(
            name = "Nuka-Cola",
            description = "Clear 1 Stress.",
            load = 1,
            id = 2006
        )
        itemMap[2007] = ItemTemplate(
            name = "Psycho",
            description = "Gain Advantage on melee attacks, and ignore the effects of Bloodied and Suppressed. You cannot use Push. Lasts 5 minutes. Suffer 2 Fatigue.",
            load = 1,
            id = 2007
        )
        itemMap[2008] = ItemTemplate(
            name = "Radaway",
            description = "Clear 10 Radiation. Usage requires an Activity performed by a character with at least 5 Medicine.",
            load = 1,
            id = 2008
        )
        itemMap[2009] = ItemTemplate(
            name = "Rax-X",
            description = "Lower Radiation damage by 10. Lasts one hour.",
            load = 1,
            id = 2009
        )

        // Gadgets
        itemMap[3000] = ItemTemplate(
            "Battle Standard",
            "Treat your CHA as 5 points higher when determining the range of abilities.",
            1,
            3000
        )
        itemMap[3001] = ItemTemplate(
            "Computer",
            description = "Boasting 64k of RAM, this machine is the pinnacle of Pre-War computational technology.",
            load = 3,
            id = 3001
        )
    }
}