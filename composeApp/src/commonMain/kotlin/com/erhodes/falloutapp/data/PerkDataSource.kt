package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.SkillRequirement
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.StatRequirement
import com.erhodes.falloutapp.model.Stats

object PerkDataSource {
    val perkMap: HashMap<Int, Perk> = HashMap()
    fun getPerkById(id: Int): Perk {
        return perkMap[id]!!
    }
    init {
        perkMap[0] = Perk(
            name = "Power Armor Training",
            description =  "You are able to properly use power armor",
            0
        )
        perkMap[1] = Perk(
            name = "Hacker",
            description =  "When hacking, you gain 1/3/5 guesses instead of 0/1/2",
            id = 1,
            StatRequirement(Stats.INTELLIGENCE, 2),
            SkillRequirement(Skills.SCIENCE, 5)
        )
        perkMap[2] = Perk(
            name = "Deadeye",
            description = "+0/0/1 damage when benefiting from Accurate.",
            id = 2,
            StatRequirement(Stats.AGILITY, 2),
            SkillRequirement(Skills.GUNS, 5)
        )
        perkMap[3] = Perk(
            name = "Scavenger",
            description = "Add 1/1/2 to Scavenge rolls.",
            id = 3,
            StatRequirement(Stats.PERCEPTION, 2),
            SkillRequirement(Skills.SURVIVAL, 5)
        )
        perkMap[4] = Perk(
            name = "Grenadier",
            description = "Add +1 to the radius of Blast weapons",
            id = 4,
            StatRequirement(Stats.AGILITY, 2),
            SkillRequirement(Skills.GUNS, 5)
        )
        perkMap[5] = Perk(
            name = "Trigger Discipline",
            description = "Lower the ammo cost of using Burst by 1.",
            id = 5,
            StatRequirement(Stats.AGILITY, 2),
            SkillRequirement(Skills.GUNS, 5)
        )
        perkMap[6] = Perk(
            name = "Incinerator",
            description = "When an enemy within 20 would suffer Burn, it triggers twice instead.",
            id = 6,
            StatRequirement(Stats.AGILITY, 2),
            SkillRequirement(Skills.GUNS, 5)
        )
        perkMap[7] = Perk(
            name = "Followup",
            description = "Move X after triggering Knockback X.",
            id = 7,
            StatRequirement(Stats.STRENGTH, 2),
            SkillRequirement(Skills.MELEE, 5)
        )
        perkMap[8] = Perk(
            name = "Hindrance",
            description = "When a creature with Vulnerability attacks you, you may spend one stack to force them to reroll a successful die (once per die).",
            id = 8,
            StatRequirement(Stats.AGILITY, 2),
            SkillRequirement(Skills.GUNS, 5)
        )
        perkMap[9] = Perk(
            name = "Zephyr",
            description = "Increase the range of Storm weapons by 1.",
            id = 9,
            StatRequirement(Stats.AGILITY, 2),
            SkillRequirement(Skills.GUNS, 5)
        )
        perkMap[10] = Perk(
            name = "Cover Fire",
            description = "After you Suppress one or more enemies, a ready ally within 10 may take their turn after yours.",
            id = 10,
            StatRequirement(Stats.AGILITY, 2),
            SkillRequirement(Skills.GUNS, 5)
        )
        perkMap[11] = Perk(
            name = "Going the Distance",
            description = "Move 2 extra spaces when charging with a Windup weapon.",
            id = 11,
            StatRequirement(Stats.STRENGTH, 2),
            SkillRequirement(Skills.MELEE, 5)
        )
        perkMap[12] = Perk(
            name = "Sprinter",
            description = "Increase your speed by 2.",
            id = 12,
            SkillRequirement(Skills.ATHLETICS, 5)
        )
        perkMap[13] = Perk(
            name = "Gadgeteer",
            description = "Once per day as an Activity, spend 1 Part to create a number of makeshift Gadgets with total crafting cost equal to your Engineering skill. You must have access to a scrapper’s lab or kit to use this Activity.",
            id = 13,
            SkillRequirement(Skills.ENGINEERING, 5)
        )
        perkMap[14] = Perk(
            "Field Surgeon",
            description = "When you perform the First Aid activity, you may spend up to 2/4/6 supplies, and you may split the healing among multiple patients.",
            id = 14,
            SkillRequirement(Skills.MEDICINE, 5)
        )
        perkMap[15] = Perk(
            name = "Locksmith",
            description = "When lockpicking, you may 1/3/5 guesses.",
            id = 15,
            SkillRequirement(Skills.SABOTAGE, 5)
        )
        perkMap[16] = Perk(
            name = "Chemist",
            description = "Once per day as an Activity, spend 1 Medical Supply to create a number of makeshift Chems with total crafting cost equal to your Science skill. You must have access to a chemist’s lab or kit to use this Activity.",
            id = 16,
            SkillRequirement(Skills.SCIENCE, 5)
        )
        perkMap[17] = Perk(
            name = "Ghost",
            description = "Add 1/1/2 to Hide checks.",
            id = 17,
            SkillRequirement(Skills.SNEAK, 5)
        )
        perkMap[18] = Perk(
            name = "Insightful",
            description = "After making a persuasion test against someone in which you scored at least 1 success, learn one of their motivations.",
            id = 18,
            StatRequirement(Stats.CHARISMA, 2),
            SkillRequirement(Skills.SPEECH, 5)
        )
        perkMap[19] = Perk(
            name = "With Me!",
            description = "When allies use Follow the Leader on your turn, they may Run instead of Dash.",
            id = 19,
            StatRequirement(Stats.CHARISMA, 2),
            SkillRequirement(Skills.SPEECH, 5)
        )
        perkMap[20] = Perk(
            name = "Frequent Flyer",
            description = "Lower Fatigue inflicted by Chems by 1.",
            id = 20,
            StatRequirement(Stats.ENDURANCE, 2),
            SkillRequirement(Skills.SURVIVAL, 5)
        )
        perkMap[21] = Perk(
            name = "Lucky Find",
            description = "You may add or subtract up to 2 on any Loot roll",
            id = 21,
            StatRequirement(Stats.LUCK, 2)
        )
        perkMap[22] = Perk(
            name = "Scavenger",
            description = "Add 1/1/2 to Scavenge rolls.",
            id = 22,
            StatRequirement(Stats.PERCEPTION, 2),
        )
        perkMap[23] = Perk(
            name = "Inspirational",
            description = "Spend 1AP: Until the start of your next turn, you may use Aid without spending a Reaction.",
            id = 23,
            StatRequirement(Stats.CHARISMA, 2),
            SkillRequirement(Skills.SPEECH, 5)
        )
    }
}