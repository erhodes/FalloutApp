package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.data.ItemDataSource.ID_ARMOR_LEATHER
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.model.action.MeleeAttack
import com.erhodes.falloutapp.model.action.RangedAttack

object EnemyDataSource {

    fun createRaiderShotgunner(): Character {
        val raiderShotgunner = Character(
            name = "Raider Shotgunner",
            strength = 2,
            perception = 2,
            endurance = 2,
            charisma = 2,
            agility = 2,
            luck = 2,
            intelligence = 2
        )
        raiderShotgunner.skills[Skills.GUNS.ordinal] = 5
        val shotgun = Weapon(ItemDataSource.getItemTemplateById(6), 1)
        raiderShotgunner.addItemToInventory(shotgun)
        raiderShotgunner.equipItem(shotgun)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderShotgunner.addItemToInventory(armor)
        raiderShotgunner.equipItem(armor)
        raiderShotgunner.actions.add(RangedAttack(shotgun))
        return raiderShotgunner
    }
    
    fun createRaiderPsycho(): Character {
        val raiderPsycho = Character(
            name = "Raider Psycho",
            strength = 3,
            perception = 2,
            endurance = 2,
            charisma = 2,
            agility = 2,
            luck = 2,
            intelligence = 2
        )
        raiderPsycho.skills[Skills.MELEE.ordinal] = 5
        val sledgehammer = Weapon(ItemDataSource.getItemTemplateById(7), 1)
        raiderPsycho.addItemToInventory(sledgehammer)
        raiderPsycho.equipItem(sledgehammer)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderPsycho.addItemToInventory(armor)
        raiderPsycho.equipItem(armor)
        raiderPsycho.actions.add(MeleeAttack(sledgehammer))
        return raiderPsycho
    }

    fun createRaiderLieutenant(): Character {
        val raiderLieutenant = Character(
            name = "Raider Lieutenant",
            strength = 1,
            perception = 2,
            endurance = 2,
            charisma = 3,
            agility = 3,
            luck = 1,
            intelligence = 2
        )
        raiderLieutenant.skills[Skills.GUNS.ordinal] = 5
        raiderLieutenant.skills[Skills.SPEECH.ordinal] = 5
        val assaultRifle = Weapon(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ASSAULT_RIFLE), 1)
        raiderLieutenant.addItemToInventory(assaultRifle)
        raiderLieutenant.equipItem(assaultRifle)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderLieutenant.addItemToInventory(armor)
        raiderLieutenant.equipItem(armor)
        raiderLieutenant.actions.add(MeleeAttack(assaultRifle))
        raiderLieutenant.traits.add(TraitDataSource.getTraitById(1))
        return raiderLieutenant
    }
}