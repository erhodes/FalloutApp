package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.BasicItem
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
            ability = listOf("", "Suppress 1", "Suppress 2"),
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


        // weapons
        itemMap[9] = WeaponTemplate(
            name = "Assault Rifle MK2",
            description = "An AK-112, a military assault rifle that saw widespread use in the early 21st century.",
            load = 2,
            id = 9,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Burst",
            range = 15,
            magazineSize = 6
        )
        itemMap[10] = WeaponTemplate(
            name = "Plasma Grenade",
            description = "Creates a blast of superheated plasma on contact.",
            load = 1,
            id = 10,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Blast 1, Consumable",
            range = 8,
            magazineSize = 0
        )
        itemMap[11] = WeaponTemplate(
            name = "Sniper Rifle",
            description = "Good for killing far-away people.",
            load = 2,
            id = 11,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Shred 3", "Shred 5"),
            passive = "Accurate",
            range = 20,
            magazineSize = 3
        )
        itemMap[12] = WeaponTemplate(
            name = "Laser Pistol MK 2",
            description = "Am AEP7 Laser Pistol. The sidearm of choice for Pre-War military officers.",
            load = 1,
            id = 12,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Ignite 3", "Ignite 5"),
            passive = "",
            range = 10,
            magazineSize = 3
        )
        itemMap[13] = WeaponTemplate(
            name = "Laser Rifle MK2",
            description = "An AER9 Laser Rifle. The most widely produced energy weapon in the world.",
            load = 2,
            id = 13,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Ignite 3", "Ignite 5"),
            passive = "Burst",
            range = 15,
            magazineSize = 6
        )
        itemMap[14] = WeaponTemplate(
            name = "Pistol MK2",
            description = "A well maintained pistol",
            load = 1,
            id = 14,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Maim 1", "Maim 2"),
            passive = "",
            range = 15,
            magazineSize = 3
        )
        itemMap[15] = WeaponTemplate(
            name = "Combat Shotgun",
            description = "A Winchester City-Killer 12 gauge shotgun, bullpup variant.",
            load = 2,
            id = 15,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Knockback 2", "Knockback 3"),
            passive = "Storm",
            range = 10,
            magazineSize = 3
        )
        itemMap[16] = WeaponTemplate(
            name = "Super Sledge",
            "A sledgehammer outfitted with a kinetic storage device.",
            load = 2,
            id = 16,
            damage = listOf(2, 3, 4),
            ability = listOf("Knockback 2", "Knockback 3", "Knockback 4"),
            passive = "Windup",
            range = 1,
            magazineSize = 0
        )
        itemMap[17] = WeaponTemplate(
            name = "Frag Mine",
            "An military issue proximity explosive.",
            load = 2,
            id = 17,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Blast 1, Trap 2, Consumable",
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
        itemMap[1002] = ArmorTemplate(
            name = "Combat Armor",
            description = "The standard body armor for Pre-War rank and file infantry",
            load = 2,
            id = 1002,
            durability = 4,
            toughness = 1
        )
        itemMap[1003] = ArmorTemplate(
            name = "T-45 Power Armor",
            description = "Manufactured by West-Tek, this was the first model of power armor to see deployment. It has seen better days, but its fusion core still provides power.",
            load = 5,
            id = 1001,
            durability = 5,
            toughness = 2
        )

        // Currencies
        itemMap[ID_RATIONS] = StackableItemTemplate(
            name = "Rations",
            description = "A day's worth of preserved, uncontaminated food.",
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
            description = "Add 1 to Analyze checks. Can also be used for simple data processing. Requires 1 power when in use.",
            load = 3,
            id = 3001
        )
        itemMap[3002] = ItemTemplate(
            name = "Doctor's Bag",
            description = "Add 1 success to any Treat Injury project.",
            load = 2,
            id = 3002
        )
        itemMap[3003] = ItemTemplate(
            name = "First Aid Kit",
            description = "Add 1 success to any First Aid test.",
            load = 1,
            id = 3003
        )
        itemMap[3004] = ItemTemplate(
            name = "Lockpick",
            description = "Add 1 success to any lockpick test.",
            load = 1,
            id = 3004
        )
        itemMap[3005] = ItemTemplate(
            name = "Hacking Tool",
            description = "Add 1 success to any hacking test.",
            load = 1,
            id = 3005
        )
        itemMap[3006] = ItemTemplate(
            name = "Padded Boots",
            description = "Gain advantage on Hide checks.",
            load = 1,
            id = 3006
        )
        itemMap[3007] = ItemTemplate(
            name = "Repair Kit",
            description = "Add 1 success to repair tests.",
            load = 1,
            id = 3007
        )
        itemMap[3008] = ItemTemplate(
            name = "Motion Sensor",
            description = "Add 1 to Awareness.",
            load = 2,
            id = 3008
        )
        itemMap[3009] = ItemTemplate(
            name = "Scope",
            description = "Adds 2 range when using Aim.",
            load = 1,
            id = 3009
        )

        // Ammo
        itemMap[4000] = StackableItemTemplate(
            "Bullets",
            description = "5.56mm rounds. Conveniently, all firearms in the region are chambered for these.",
            load = 1,
            id = 4000,
            max = 10
        )
        itemMap[4001] = StackableItemTemplate(
            "Shells",
            description = "Shotgun shells.",
            load = 1,
            id = 4001,
            max = 10
        )
        itemMap[4002] = StackableItemTemplate(
            "Cells",
            description = "Power cells. For use with energy weapons and other electronic devices",
            load = 1,
            id = 4002,
            max = 10
        )

        itemMap[4003] = StackableItemTemplate(
            "Armor Piercing 5.56 Rounds",
            description = "Armor-piercing 5.56mm rounds. Add 2 Shred.",
            load = 1,
            id = 4003,
            max = 3
        )
        itemMap[4004] = StackableItemTemplate(
            "Blade Attachment",
            description = "A small blade attachment for melee weapons. Add 1 damage.",
            load = 1,
            id = 4004,
            max = 3
        )
        itemMap[4005] = StackableItemTemplate(
            "Breach Shells",
            description = "Special shotgun shells designed to punch through armor. Add 1 damage.",
            load = 1,
            id = 4005,
            max = 3
        )
        itemMap[4006] = StackableItemTemplate(
            "Dragon's Breath Shells",
            description = "Incendiary shotgun shells. Add 2 Ignite.",
            load = 1,
            id = 4006,
            max = 3
        )
        itemMap[4007] = StackableItemTemplate(
            "Gamma Wave Cell",
            description = "A cell charged with gamma radiation. Add 1 Knockback.",
            load = 1,
            id = 4007,
            max = 3
        )
        itemMap[4008] = StackableItemTemplate(
            "High Energy Cell",
            description = "A high-energy power cell for advanced energy weapons. Add 2 Ignite.",
            load = 1,
            id = 4008,
            max = 3
        )
        itemMap[4009] = StackableItemTemplate(
            "High Impact 5.56 Rounds",
            description = "Heavy 5.56mm rounds engineered for extra stopping power. Add 1 damage.",
            load = 1,
            id = 4009,
            max = 3
        )
        itemMap[4010] = StackableItemTemplate(
            "Incendiary 5.56 Rounds",
            description = "Incendiary 5.56mm rounds. Add 2 Ignite.",
            load = 1,
            id = 4010,
            max = 3
        )
        itemMap[4011] = StackableItemTemplate(
            "Overcharged Cell",
            description = "An overcharged power cell. Add 1 damage.",
            load = 1,
            id = 4011,
            max = 3
        )
        itemMap[4012] = StackableItemTemplate(
            "Slug Shells",
            description = "Single projectile shotgun shells. Add 1 Knockback.",
            load = 1,
            id = 4012,
            max = 3
        )
        itemMap[4013] = StackableItemTemplate(
            "Shrieker 5.56 Rounds",
            description = "Special rounds that emit a horrible sound while whistling through the air. Add 1 Suppress.",
            load = 1,
            id = 4013,
            max = 3
        )

        // Magazines
        itemMap[5000] = ItemTemplate(
            name = "Tesla Science Issue 12: Maximum Burn",
            description = "Teaches Incinerator",
            load = 1,
            id = 5000
        )
        itemMap[5001] = ItemTemplate(
            name = "Grognak the Barbarian: Zardak’s Revenge",
            description = "Teaches Followup",
            load = 1,
            id = 5001
        )
        itemMap[5002] = ItemTemplate(
            name = "Guns and Bullets",
            description = "Teaches Cover Fire",
            load = 1,
            id = 5002
        )
        itemMap[5003] = ItemTemplate(
            name = "Alaskan Sniper: A True American Tale",
            description = "Teaches Hindrance",
            load = 1,
            id = 5003
        )
        itemMap[5004] = ItemTemplate(
            name = "Flee, Mortals!",
            description = "Teaches Sprinter",
            load = 1,
            id = 5004
        )
        itemMap[5005] = ItemTemplate(
            name = "The Wire: The Complete History",
            description = "Teaches Frequent Flyer",
            load = 1,
            id = 5005
        )
        itemMap[5006] = ItemTemplate(
            name = "Dean’s Electronics",
            description = "Teaches Hacker",
            load = 1,
            id = 5006
        )
        itemMap[5007] = ItemTemplate(
            name = "Scout Handbook",
            description = "Teaches Lucky Find",
            load = 1,
            id = 5007
        )
        itemMap[5008] = ItemTemplate(
            name = "Tumblers Today",
            description = "Teaches Locksmith",
            load = 1,
            id = 5008
        )
        itemMap[5009] = ItemTemplate(
            name = "US Covert Operations Manual",
            description = "Ghost",
            load = 1,
            id = 5008
        )
    }
}