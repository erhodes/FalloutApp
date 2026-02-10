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
    const val ID_FRAG_GRENADE = 1
    const val ID_THROWING_KNIVES = 20
    const val ID_RATIONS = 1996
    const val ID_CAPS = 1997
    init {
        // weapons
        itemMap[0] = WeaponTemplate(
            name = "Assault Rifle",
            description = "An old AK-47. Its famed reliability ensures it continues to spew bullets decades after the end of civilization.",
            load = 2,
            tier = 1,
            id = 0,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Suppress 1", "Suppress 2"),
            passive = "Burst",
            range = 15,
            magazineSize = 0
        )
        itemMap[1] = WeaponTemplate(
            name = "Frag Grenade",
            description = "A MK2 fragmentation-type anti-personnel hand grenade.",
            load = 1,
            tier = 1,
            id = 1,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Blast 1, Consumable",
            range = 8,
            magazineSize = 1
        )
        itemMap[2] = WeaponTemplate(
            name = "Hunting Rifle",
            description = "A bolt-action rifle designed for hunting game animals.",
            load = 2,
            tier = 1,
            id = 2,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Shred 2", "Shred 4"),
            passive = "Accurate",
            range = 20,
            magazineSize = 0
        )
        itemMap[3] = WeaponTemplate(
            name = "Laser Pistol",
            description = "A worn out AEP7 Laser Pistol. Smells like burning ozone when fired",
            load = 1,
            tier = 1,
            id = 3,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Ignite 2", "Ignite 4"),
            passive = "",
            range = 10,
            magazineSize = 0
        )
        itemMap[4] = WeaponTemplate(
            name = "Laser Rifle",
            description = "A worn out AER9 Laser Rifle. Smells like burning ozone when fired",
            load = 2,
            tier = 1,
            id = 4,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Ignite 2", "Ignite 4"),
            passive = "Burst",
            range = 15,
            magazineSize = 0
        )
        itemMap[5] = WeaponTemplate(
            name = "Pistol",
            description = "An N99 sideam. Lightweight and effective at close ranges.",
            load = 1,
            tier = 1,
            id = 5,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Maim 1", "Maim 2"),
            passive = "",
            range = 15,
            magazineSize = 0
        )
        itemMap[6] = WeaponTemplate(
            name = "Shotgun",
            description = "A Winchester Widowmaker double-barreled 12 gauge shotgun. A short barrel, with mahogany grip.",
            load = 2,
            tier = 1,
            id = 6,
            damage = listOf(1, 2, 3),
            ability = listOf("", "Knockback 1", "Knockback 2"),
            passive = "Storm",
            range = 10,
            magazineSize = 0
        )
        itemMap[7] = WeaponTemplate(
            name = "Sledgehammer",
            description = "Equally effective at smashing walls and skulls",
            load = 2,
            tier = 1,
            id = 7,
            damage = listOf(1, 2, 3),
            ability = listOf("Knockback 1", "Knockback 2", "Knockback 3"),
            passive = "Windup",
            range = 1,
            magazineSize = 0
        )
        itemMap[8] = WeaponTemplate(
            name = "Mine",
            description = "An improvised proximity explosive.",
            load = 2,
            tier = 1,
            id = 8,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Blast 1, Trap 1, Consumable",
            range = 1,
            magazineSize = 0
        )
        itemMap[19] = WeaponTemplate(
            name = "Blade",
            description = "A short, bladed weapon. Maybe a dagger or a knife.",
            load = 1,
            tier = 1,
            id = 19,
            damage = listOf(1, 2, 3),
            ability = listOf("", "", ""),
            passive = "Nimble 1",
            range = 1,
            magazineSize = 0
        )
        itemMap[20] = WeaponTemplate(
            name = "Throwing Knives",
            description = "A simple knife, balanced for throwing.",
            load = 1,
            tier = 1,
            id = 20,
            damage = listOf(1, 2, 3),
            ability = listOf("", "", ""),
            passive = "Recoverable",
            range = 8,
            magazineSize = 3
        )



        // weapons
        itemMap[9] = WeaponTemplate(
            name = "Assault Rifle MK2",
            description = "An AK-112, a military assault rifle that saw widespread use in the early 21st century.",
            load = 2,
            tier = 2,
            id = 9,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Burst",
            range = 15,
            magazineSize = 0
        )
        itemMap[10] = WeaponTemplate(
            name = "Plasma Grenade",
            description = "Creates a blast of superheated plasma on contact.",
            load = 1,
            tier = 2,
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
            tier = 2,
            id = 11,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Shred 3", "Shred 5"),
            passive = "Accurate",
            range = 20,
            magazineSize = 0
        )
        itemMap[12] = WeaponTemplate(
            name = "Laser Pistol MK 2",
            description = "Am AEP7 Laser Pistol. The sidearm of choice for Pre-War military officers.",
            load = 1,
            tier = 2,
            id = 12,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Ignite 3", "Ignite 5"),
            passive = "",
            range = 10,
            magazineSize = 0
        )
        itemMap[13] = WeaponTemplate(
            name = "Laser Rifle MK2",
            description = "An AER9 Laser Rifle. The most widely produced energy weapon in the world.",
            load = 2,
            tier = 2,
            id = 13,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Ignite 3", "Ignite 5"),
            passive = "Burst",
            range = 15,
            magazineSize = 0
        )
        itemMap[14] = WeaponTemplate(
            name = "Pistol MK2",
            description = "A well maintained pistol",
            load = 1,
            tier = 2,
            id = 14,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Maim 1", "Maim 2"),
            passive = "",
            range = 15,
            magazineSize = 0
        )
        itemMap[15] = WeaponTemplate(
            name = "Combat Shotgun",
            description = "A Winchester City-Killer 12 gauge shotgun, bullpup variant.",
            load = 2,
            tier = 2,
            id = 15,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Knockback 2", "Knockback 3"),
            passive = "Storm",
            range = 10,
            magazineSize = 0
        )
        itemMap[16] = WeaponTemplate(
            name = "Super Sledge",
            description = "A sledgehammer outfitted with a kinetic storage device.",
            load = 2,
            tier = 2,
            id = 16,
            damage = listOf(2, 3, 4),
            ability = listOf("Knockback 2", "Knockback 3", "Knockback 4"),
            passive = "Windup",
            range = 1,
            magazineSize = 0
        )
        itemMap[17] = WeaponTemplate(
            name = "Frag Mine",
            description = "An military issue proximity explosive.",
            load = 2,
            tier = 2,
            id = 17,
            damage = listOf(3, 4, 5),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Blast 1, Trap 2, Consumable",
            range = 1,
            magazineSize = 0
        )
        itemMap[18] = WeaponTemplate(
            name = "Minigun",
            description = "A Rockwell CZ53 Personal Minigun. A multi-barrelled chaingun firing over 60,000 RPM.",
            load = 2,
            tier = 2,
            id = 18,
            damage = listOf(3, 4, 5),
            ability = listOf("", "Suppress 2", "Suppress 3"),
            passive = "Heavy, Burst, Burst Only",
            range = 10,
            magazineSize = 0
        )
        itemMap[21] = WeaponTemplate(
            name = "Blade MK2",
            description = "A real nasty looking blade.",
            load = 1,
            tier = 2,
            id = 21,
            damage = listOf(2, 3, 4),
            ability = listOf("", "", ""),
            passive = "Nimble 1",
            range = 1,
            magazineSize = 0
        )
        itemMap[22] = WeaponTemplate(
            name = "Throwing Knives MK2",
            description = "A carbon fiber knife, balanced for throwing.",
            load = 1,
            tier = 2,
            id = 22,
            damage = listOf(2, 3, 4),
            ability = listOf("", "", ""),
            passive = "Recoverable",
            range = 8,
            magazineSize = 3
        )
        itemMap[23] = WeaponTemplate(
            name = "Shishkebab",
            description = "A sword with a small propane flamethrower attached.",
            load = 2,
            tier = 2,
            id = 23,
            damage = listOf(2, 3, 4),
            ability = listOf("", "Ignite 3", "Ignite 4"),
            passive = "Windup",
            range = 0,
            magazineSize = 0
        )

        // Armors
        itemMap[1000] = ArmorTemplate(
            name = "Leather Armor",
            description = "Layers of boiled leather provide adequate protection.",
            load = 2,
            tier = 1,
            id = 1000,
            durability = 3,
            toughness = 0
        )
        itemMap[1001] = ArmorTemplate(
            name = "Raider Power Armor",
            description = "A very poorly maintained suit of power armor. Beneath all the rusted metal, the frame still works.",
            load = 5,
            tier = 1,
            id = 1001,
            durability = 4,
            toughness = 1
        )
        itemMap[1002] = ArmorTemplate(
            name = "Combat Armor",
            description = "The standard body armor for Pre-War rank and file infantry",
            load = 2,
            tier = 2,
            id = 1002,
            durability = 4,
            toughness = 1
        )
        itemMap[1003] = ArmorTemplate(
            name = "T-45 Power Armor",
            description = "Manufactured by West-Tek, this was the first model of power armor to see deployment. It has seen better days, but its fusion core still provides power.",
            load = 5,
            tier = 2,
            id = 1003,
            durability = 5,
            toughness = 2
        )

        // Currencies
        itemMap[1995] = StackableItemTemplate(
            name = "Irradiated Rations",
            description = "A day's worth of preserved, lightly contaminated food. Inflicts 2 Rads if eaten.",
            load = 1,
            tier = 1,
            id = 1995,
            max = 10
        )
        itemMap[ID_RATIONS] = StackableItemTemplate(
            name = "Rations",
            description = "A day's worth of preserved, uncontaminated food.",
            load = 1,
            tier = 1,
            id = ID_RATIONS,
            max = 10
        )
        itemMap[ID_CAPS] = StackableItemTemplate(
            name = "Caps",
            description = "The lifeblood of the post-nuclear economy.",
            load = 1,
            tier = 1,
            id = ID_CAPS,
            max = 50
        )
        itemMap[1998] = StackableItemTemplate(
            name = "Medical Supplies",
            description = "An assortment of medical supplies, suitable for treating injuries and crafting chems.",
            load = 1,
            tier = 1,
            id = 1998,
            max = 10
        )
        itemMap[1999] = StackableItemTemplate(
            name = "Parts",
            description = "An assortment of spare parts, suitable for making repairs and crafting gadgets.",
            load = 1,
            tier = 1,
            id = 1999,
            max = 10
        )

        // Chems

        itemMap[2000] = ItemTemplate(
            name = "Stimpak",
            description = "A medicinal chem. Heals 2 damage.",
            load = 1,
            tier = 1,
            id = 2000
        )
        itemMap[2001] = ItemTemplate(
            name = "Super Stimpak",
            description = "An advanced healing chem. Heals all damage suffered, but inflicts a point of Fatigue.",
            load = 1,
            tier = 1,
            id = 2001
        )
        itemMap[2002] = ItemTemplate(
            name = "Buffout",
            description = "Grants Advantage on all Strength, Endurance, and Agility checks, but you cannot Push. Lasts 30 minutes. Suffer 1 Fatigue.",
            load = 1,
            tier = 1,
            id = 2002
        )
        itemMap[2003] = ItemTemplate(
            name = "Jet",
            description = "Gain an additional AP every turn for 1 minute. You cannot use Push. Suffer 2 Fatigue.",
            load = 1,
            tier = 2,
            id = 2003
        )
        itemMap[2004] = ItemTemplate(
            name = "Med-X",
            description = "Ignore the Bloodied condition for 1 minute. Suffer 1 Fatigue.",
            load = 1,
            tier = 1,
            id = 2004
        )
        itemMap[2005] = ItemTemplate(
            name = "Mentats",
            description = "Grants Advantage on all Perception, Charisma, and Intelligence checks, but you cannot Push. Lasts 30 minutes. Suffer 1 Fatigue.",
            load = 1,
            tier = 1,
            id = 2005
        )
        itemMap[2006] = ItemTemplate(
            name = "Nuka-Cola",
            description = "Clear 1 Stress.",
            load = 1,
            tier = 1,
            id = 2006
        )
        itemMap[2007] = ItemTemplate(
            name = "Psycho",
            description = "Gain Advantage on melee attacks, and ignore the effects of Bloodied and Suppressed. You cannot use Push. Lasts 5 minutes. Suffer 2 Fatigue.",
            load = 1,
            tier = 1,
            id = 2007
        )
        itemMap[2008] = ItemTemplate(
            name = "Radaway",
            description = "Clear 10 Radiation. Usage requires an Activity performed by a character with at least 5 Medicine.",
            load = 1,
            tier = 1,
            id = 2008
        )
        itemMap[2009] = ItemTemplate(
            name = "Rad-X",
            description = "Lower Radiation damage by 10. Lasts one hour.",
            load = 1,
            tier = 1,
            id = 2009
        )

        // Gadgets
        itemMap[3000] = ItemTemplate(
            name = "Battle Standard",
            description = "Treat your CHA as 5 points higher when determining the range of abilities.",
            load = 1,
            tier = 1,
            id = 3000
        )
        itemMap[3001] = ItemTemplate(
            name = "Computer",
            description = "Add 1 to Analyze checks. Can also be used for simple data processing. Requires 1 power when in use.",
            load = 3,
            tier = 1,
            id = 3001
        )
        itemMap[3002] = ItemTemplate(
            name = "Doctor's Bag",
            description = "Add 1 success to any Treat Injury project.",
            load = 2,
            tier = 1,
            id = 3002
        )
        itemMap[3003] = ItemTemplate(
            name = "First Aid Kit",
            description = "Add 1 success to any First Aid test.",
            load = 1,
            tier = 1,
            id = 3003
        )
        itemMap[3004] = ItemTemplate(
            name = "Lockpick",
            description = "Add 1 success to any lockpick test.",
            load = 1,
            tier = 1,
            id = 3004
        )
        itemMap[3005] = ItemTemplate(
            name = "Hacking Tool",
            description = "Add 1 success to any hacking test.",
            load = 1,
            tier = 1,
            id = 3005
        )
        itemMap[3006] = ItemTemplate(
            name = "Padded Boots",
            description = "Gain advantage on Hide checks.",
            load = 1,
            tier = 1,
            id = 3006
        )
        itemMap[3007] = ItemTemplate(
            name = "Repair Kit",
            description = "Add 1 success to repair tests.",
            load = 1,
            tier = 1,
            id = 3007
        )
        itemMap[3008] = ItemTemplate(
            name = "Motion Sensor",
            description = "Add 1 to Awareness.",
            load = 2,
            tier = 1,
            id = 3008
        )
        itemMap[3009] = ItemTemplate(
            name = "Scope",
            description = "Adds 2 range when using Aim.",
            load = 1,
            tier = 1,
            id = 3009
        )
        itemMap[3010] = ItemTemplate(
            name = "Ablative Plating",
            description = "Adds 2 Toughness against a single attack",
            load = 1,
            tier = 1,
            id = 3010
        )
        itemMap[3011] = ItemTemplate(
            name = "Caltrops",
            description = "Create difficult terrain in a Burst 1 area within range 8.",
            load = 1,
            tier = 1,
            id = 3011
        )
        itemMap[3012] = StackableItemTemplate(
            name = "Concussion Grenade",
            description = "Range 8. Burst 1, Knockback 1",
            load = 1,
            tier = 1,
            id = 3012,
            max = 1
        )
        itemMap[3013] = StackableItemTemplate(
            name = "Flashbang",
            description = "Range 8. Burst 1, lowers enemy effective Perception by 2 until the end of your next turn.",
            load = 1,
            tier = 1,
            id = 3013,
            max = 1
        )
        itemMap[3014] = ItemTemplate(
            name = "Holo Projector",
            description = "Program this amazing Lightmaster Holo Projector to display any image you can imagine! It will then do so for one whole minute before burning out its lenses forever.",
            load = 2,
            tier = 1,
            id = 3014
        )
        itemMap[3015] = StackableItemTemplate(
            name = "Molotov Cocktail",
            description = "Burst 1, create an area of hazardous terrain. Anyone starting in, or passing through, takes 1 damage and 1 Burn",
            load = 1,
            tier = 1,
            id = 3015,
            max = 1
        )
        itemMap[3016] = StackableItemTemplate(
            name = "Perfume",
            description = "For 1 day, gain advantage on any Charisma check made to influence a person who is close enough to smell you. Most people in the Wasteland do not smell good; you can be the exception!",
            load = 1,
            tier = 1,
            id = 3016,
            max = 3
        )
        itemMap[3017] = ItemTemplate(
            name = "Repeller",
            description = "Reaction: Knockback 1 all adjacent creatures.",
            load = 1,
            tier = 1,
            id = 3017
        )
        itemMap[3018] = ItemTemplate(
            name = "Screecher",
            description = "A voice modulator that distorts the user’s voice in terrifying ways. Roll Charisma + Speech against all enemies within 10. Inflict Suppress 2/3/4 against them. The modulator burns out after a single use.",
            load = 1,
            tier = 1,
            id = 3018
        )
        itemMap[3019] = StackableItemTemplate(
            name = "Smoke Grenade",
            description = "Create a Burst 1 cloud of obscuring smoke anywhere within 8.",
            load = 1,
            tier = 1,
            id = 3019,
            max = 1
        )
        itemMap[3020] = StackableItemTemplate(
            name = "Vortex Grenade",
            description = "Select a square within 8. All creatures within 3 of that square are pushed 1 towards it.",
            load = 1,
            tier = 1,
            id = 3020,
            max = 1
        )

        // Ammo
        itemMap[4000] = StackableItemTemplate(
            name = "Bullets",
            description = "5.56mm rounds. Conveniently, all firearms in the region are chambered for these.",
            load = 1,
            tier = 1,
            id = 4000,
            max = 10
        )
        itemMap[4001] = StackableItemTemplate(
            name = "Shells",
            description = "Shotgun shells.",
            load = 1,
            tier = 1,
            id = 4001,
            max = 10
        )
        itemMap[4002] = StackableItemTemplate(
            name = "Cells",
            description = "Power cells. For use with energy weapons and other electronic devices",
            load = 1,
            tier = 1,
            id = 4002,
            max = 10
        )

        itemMap[4003] = StackableItemTemplate(
            name = "Armor Piercing 5.56 Rounds",
            description = "Armor-piercing 5.56mm rounds. Add 2 Shred.",
            load = 1,
            tier = 1,
            id = 4003,
            max = 3
        )
        itemMap[4004] = StackableItemTemplate(
            name = "Blade Attachment",
            description = "A small blade attachment for melee weapons. Add 1 damage.",
            load = 1,
            tier = 1,
            id = 4004,
            max = 3
        )
        itemMap[4005] = StackableItemTemplate(
            name = "Breach Shells",
            description = "Special shotgun shells designed to punch through armor. Add 1 damage.",
            load = 1,
            tier = 1,
            id = 4005,
            max = 3
        )
        itemMap[4006] = StackableItemTemplate(
            name = "Dragon's Breath Shells",
            description = "Incendiary shotgun shells. Add 2 Ignite.",
            load = 1,
            tier = 1,
            id = 4006,
            max = 3
        )
        itemMap[4007] = StackableItemTemplate(
            name = "Gamma Wave Cell",
            description = "A cell charged with gamma radiation. Add 1 Knockback.",
            load = 1,
            tier = 1,
            id = 4007,
            max = 3
        )
        itemMap[4008] = StackableItemTemplate(
            name = "High Energy Cell",
            description = "A high-energy power cell for advanced energy weapons. Add 2 Ignite.",
            load = 1,
            tier = 1,
            id = 4008,
            max = 3
        )
        itemMap[4009] = StackableItemTemplate(
            name = "High Impact 5.56 Rounds",
            description = "Heavy 5.56mm rounds engineered for extra stopping power. Add 1 damage.",
            load = 1,
            tier = 1,
            id = 4009,
            max = 3
        )
        itemMap[4010] = StackableItemTemplate(
            name = "Incendiary 5.56 Rounds",
            description = "Incendiary 5.56mm rounds. Add 2 Ignite.",
            load = 1,
            tier = 1,
            id = 4010,
            max = 3
        )
        itemMap[4011] = StackableItemTemplate(
            name = "Overcharged Cell",
            description = "An overcharged power cell. Add 1 damage.",
            load = 1,
            tier = 1,
            id = 4011,
            max = 3
        )
        itemMap[4012] = StackableItemTemplate(
            name = "Slug Shells",
            description = "Single projectile shotgun shells. Add 1 Knockback.",
            load = 1,
            tier = 1,
            id = 4012,
            max = 3
        )
        itemMap[4013] = StackableItemTemplate(
            name = "Shrieker 5.56 Rounds",
            description = "Special rounds that emit a horrible sound while whistling through the air. Add 1 Suppress.",
            load = 1,
            tier = 1,
            id = 4013,
            max = 3
        )
        itemMap[4014] = StackableItemTemplate(
            name = "Fire Punch Attachment",
            description = "A cannister of pyrophoric liquid that shatters when struck. Adds 2 Ignite to a melee weapon attack.",
            load = 1,
            tier = 1,
            id = 4014,
            max = 3
        )

        // Magazines
        itemMap[5000] = ItemTemplate(
            name = "Tesla Science Issue 12: Maximum Burn",
            description = "Teaches Incinerator",
            load = 1,
            tier = 1,
            id = 5000
        )
        itemMap[5001] = ItemTemplate(
            name = "Grognak the Barbarian: Zardak’s Revenge",
            description = "Teaches Followup",
            load = 1,
            tier = 1,
            id = 5001
        )
        itemMap[5002] = ItemTemplate(
            name = "Guns and Bullets",
            description = "Teaches Cover Fire",
            load = 1,
            tier = 1,
            id = 5002
        )
        itemMap[5003] = ItemTemplate(
            name = "Alaskan Sniper: A True American Tale",
            description = "Teaches Hindrance",
            load = 1,
            tier = 1,
            id = 5003
        )
        itemMap[5004] = ItemTemplate(
            name = "Flee, Mortals!",
            description = "Teaches Sprinter",
            load = 1,
            tier = 1,
            id = 5004
        )
        itemMap[5005] = ItemTemplate(
            name = "The Wire: The Complete History",
            description = "Teaches Frequent Flyer",
            load = 1,
            tier = 1,
            id = 5005
        )
        itemMap[5006] = ItemTemplate(
            name = "Dean’s Electronics",
            description = "Teaches Hacker",
            load = 1,
            tier = 1,
            id = 5006
        )
        itemMap[5007] = ItemTemplate(
            name = "Scout Handbook",
            description = "Teaches Lucky Find",
            load = 1,
            tier = 1,
            id = 5007
        )
        itemMap[5008] = ItemTemplate(
            name = "Tumblers Today",
            description = "Teaches Locksmith",
            load = 1,
            tier = 1,
            id = 5008
        )
        itemMap[5009] = ItemTemplate(
            name = "US Covert Operations Manual",
            description = "Teaches Ghost",
            load = 1,
            tier = 1,
            id = 5009
        )
        itemMap[5010] = ItemTemplate(
            name = "US Army Power Armor Operating Manual",
            description = "Teaches Power Armor Training",
            load = 1,
            tier = 1,
            id = 5010
        )
    }
}
