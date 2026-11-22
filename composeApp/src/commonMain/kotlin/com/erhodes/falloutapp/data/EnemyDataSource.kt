package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.data.EnemyItemDataSource.ID_LIGHTNING_ARC
import com.erhodes.falloutapp.data.EnemyItemDataSource.ID_RADIOACTIVE_FISTS
import com.erhodes.falloutapp.data.ItemDataSource.ID_ARMOR_COMBAT
import com.erhodes.falloutapp.data.ItemDataSource.ID_ARMOR_LEATHER
import com.erhodes.falloutapp.data.ItemDataSource.ID_ASSAULT_RIFLE
import com.erhodes.falloutapp.data.ItemDataSource.ID_ASSAULT_RIFLE_MK2
import com.erhodes.falloutapp.data.ItemDataSource.ID_BLADE_MK2
import com.erhodes.falloutapp.data.ItemDataSource.ID_COMBAT_SHOTGUN
import com.erhodes.falloutapp.data.ItemDataSource.ID_LASER_PISTOL_MK2
import com.erhodes.falloutapp.data.ItemDataSource.ID_MINIGUN
import com.erhodes.falloutapp.data.ItemDataSource.ID_PA_RAIDER
import com.erhodes.falloutapp.data.ItemDataSource.ID_SHOTGUN
import com.erhodes.falloutapp.data.ItemDataSource.ID_SHRIEKER_ROUNDS
import com.erhodes.falloutapp.data.ItemDataSource.ID_SLEDGEHAMMER
import com.erhodes.falloutapp.data.ItemDataSource.ID_SNIPER_RIFLE
import com.erhodes.falloutapp.data.ItemDataSource.ID_SUPER_SLEDGE
import com.erhodes.falloutapp.data.TraitDataSource.ID_ASSASSIN
import com.erhodes.falloutapp.data.TraitDataSource.ID_COORDINATED_ASSAULT
import com.erhodes.falloutapp.data.TraitDataSource.ID_FEAR_EXPLOITER
import com.erhodes.falloutapp.data.TraitDataSource.ID_GET_HIM
import com.erhodes.falloutapp.data.TraitDataSource.ID_INFLEXIBLE_PROGRAMMING
import com.erhodes.falloutapp.data.TraitDataSource.ID_POSITIVE_CHARGE
import com.erhodes.falloutapp.data.TraitDataSource.ID_STEALTHY
import com.erhodes.falloutapp.data.TraitDataSource.ID_SWARM_TACTICS
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.Enemy
import com.erhodes.falloutapp.model.EnemyStack
import com.erhodes.falloutapp.model.EnemyTemplate
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.carried
import com.erhodes.falloutapp.model.melee
import com.erhodes.falloutapp.model.ranged
import com.erhodes.falloutapp.model.rangedAndMelee

/**
 * Catalog of enemy types. To add an enemy: declare an [EnemyTemplate] below and add it to [all].
 * The `id` is persisted on every [Enemy] built from it, so don't change it once used.
 */
object EnemyDataSource {

    val RAIDER_SHOTGUNNER = EnemyTemplate(
        id = "raider_shotgunner",
        name = "Raider Shotgunner",
        strength = 2, perception = 2, endurance = 2, charisma = 2, intelligence = 2, agility = 2, luck = 2,
        skills = mapOf(Skills.GUNS to 5),
        weapons = listOf(ranged(ID_SHOTGUN)),
        armorId = ID_ARMOR_LEATHER,
    )

    val RAIDER_PSYCHO = EnemyTemplate(
        id = "raider_psycho",
        name = "Raider Psycho",
        strength = 3, perception = 2, endurance = 2, charisma = 2, intelligence = 2, agility = 2, luck = 2,
        skills = mapOf(Skills.MELEE to 5),
        weapons = listOf(melee(ID_SLEDGEHAMMER)),
        armorId = ID_ARMOR_LEATHER,
    )

    val RAIDER_LIEUTENANT = EnemyTemplate(
        id = "raider_lieutenant",
        name = "Raider Lieutenant",
        strength = 1, perception = 2, endurance = 2, charisma = 3, intelligence = 2, agility = 3, luck = 1,
        skills = mapOf(Skills.GUNS to 5, Skills.SPEECH to 5),
        weapons = listOf(ranged(ID_ASSAULT_RIFLE)),
        armorId = ID_ARMOR_LEATHER,
        traitIds = listOf(ID_GET_HIM),
    )

    val RAIDER_HEAVY = EnemyTemplate(
        id = "raider_heavy",
        name = "Raider Heavy",
        strength = 3, perception = 2, endurance = 3, charisma = 1, intelligence = 1, agility = 3, luck = 1,
        skills = mapOf(Skills.GUNS to 6),
        weapons = listOf(ranged(ID_MINIGUN)),
        armorId = ID_PA_RAIDER,
    )

    val RAIDER_STALKER = EnemyTemplate(
        id = "raider_stalker",
        name = "Raider Stalker",
        strength = 2, perception = 3, endurance = 2, charisma = 1, intelligence = 2, agility = 3, luck = 1,
        skills = mapOf(Skills.GUNS to 6, Skills.SNEAK to 6),
        weapons = listOf(ranged(ID_SNIPER_RIFLE)),
        armorId = ID_ARMOR_LEATHER,
        traitIds = listOf(ID_ASSASSIN, ID_STEALTHY),
    )

    val ZAPPER = EnemyTemplate(
        id = "zapper",
        name = "Zapper",
        strength = 2, perception = 2, endurance = 3, charisma = 1, intelligence = 1, agility = 3, luck = 2,
        skills = mapOf(Skills.GUNS to 5, Skills.ATHLETICS to 5),
        weapons = listOf(ranged(ID_LIGHTNING_ARC)),
        traitIds = listOf(ID_POSITIVE_CHARGE),
    )

    val GHOUL = EnemyTemplate(
        id = "ghoul",
        name = "Ghoul",
        strength = 3, perception = 2, endurance = 3, charisma = 1, intelligence = 1, agility = 2, luck = 2,
        skills = mapOf(Skills.MELEE to 5),
        weapons = listOf(melee(ID_RADIOACTIVE_FISTS)),
        traitIds = listOf(ID_SWARM_TACTICS, ID_COORDINATED_ASSAULT),
    )

    val PROTECTRON = EnemyTemplate(
        id = "protectron",
        name = "Protectron",
        strength = 2, perception = 2, endurance = 3, charisma = 1, intelligence = 3, agility = 2, luck = 1,
        skills = mapOf(Skills.GUNS to 8, Skills.MELEE to 6),
        weapons = listOf(rangedAndMelee(ID_LASER_PISTOL_MK2)),
        armorId = ID_ARMOR_COMBAT,
        traitIds = listOf(ID_INFLEXIBLE_PROGRAMMING),
    )

    // An assault rifle enemy specializing in inflicting Fear
    val SOLDIER_RIFLEMAN = EnemyTemplate(
        id = "soldier_rifleman",
        name = "Soldier Rifleman",
        strength = 1, perception = 2, endurance = 2, charisma = 3, intelligence = 2, agility = 4, luck = 1,
        skills = mapOf(Skills.GUNS to 8, Skills.MELEE to 6),
        health = 6,
        weapons = listOf(ranged(ID_ASSAULT_RIFLE_MK2), carried(ID_BLADE_MK2)),
        armorId = ID_ARMOR_COMBAT,
        stacks = listOf(EnemyStack(ID_SHRIEKER_ROUNDS, 6)),
        traitIds = listOf(ID_FEAR_EXPLOITER),
    )

    // A shotgun and sledgehammer wielding enemy focusing on Knockback and protecting ranged allies.
    // Should he have a trait? Is gear enough for human enemies?
    val SOLDIER_BRUTE = EnemyTemplate(
        id = "soldier_brute",
        name = "Soldier Brute",
        strength = 2, perception = 2, endurance = 2, charisma = 3, intelligence = 1, agility = 3, luck = 1,
        skills = mapOf(Skills.GUNS to 8, Skills.MELEE to 6),
        health = 6,
        weapons = listOf(ranged(ID_COMBAT_SHOTGUN), carried(ID_SUPER_SLEDGE)),
        armorId = ID_ARMOR_COMBAT,
    )

    val SOLDIER_SNIPER = EnemyTemplate(
        id = "soldier_sniper",
        name = "Soldier Sniper",
        strength = 1, perception = 3, endurance = 2, charisma = 3, intelligence = 2, agility = 4, luck = 1,
        skills = mapOf(Skills.GUNS to 8, Skills.MELEE to 6),
        health = 6,
        weapons = listOf(ranged(ID_SNIPER_RIFLE)),
        armorId = ID_ARMOR_COMBAT,
    )

    val all: List<EnemyTemplate> = listOf(
        RAIDER_SHOTGUNNER,
        RAIDER_PSYCHO,
        RAIDER_LIEUTENANT,
        RAIDER_HEAVY,
        RAIDER_STALKER,
        ZAPPER,
        GHOUL,
        PROTECTRON,
        SOLDIER_RIFLEMAN,
        SOLDIER_BRUTE,
        SOLDIER_SNIPER,
    )

    fun getById(id: String): EnemyTemplate? = all.find { it.id == id }

    /**
     * Rebuilds the transient state (actions) of a deserialized enemy from its template.
     * A no-op for player characters and for enemies whose template is unknown.
     */
    fun restore(character: Character) {
        if (character !is Enemy) return
        getById(character.templateId)?.buildActions(character)
    }

    fun restore(encounter: Encounter) {
        encounter.characters.forEach { restore(it) }
    }
}
