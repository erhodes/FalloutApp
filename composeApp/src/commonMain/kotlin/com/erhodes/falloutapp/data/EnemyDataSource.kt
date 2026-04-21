package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.data.ItemDataSource.ID_ARMOR_LEATHER
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Weapon

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
        val shotgun = Weapon(ItemDataSource.getItemTemplateById(6), 1)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderShotgunner.addItemToInventory(shotgun)
        raiderShotgunner.equipItem(shotgun)
        raiderShotgunner.addItemToInventory(armor)
        raiderShotgunner.equipItem(armor)
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
        val shotgun = Weapon(ItemDataSource.getItemTemplateById(6), 1)
        raiderPsycho.addItemToInventory(shotgun)
        raiderPsycho.equipItem(shotgun)
        val armor = Armor(ItemDataSource.getItemTemplateById(ID_ARMOR_LEATHER), 0)
        raiderPsycho.addItemToInventory(armor)
        raiderPsycho.equipItem(armor)
        return raiderPsycho
    }
}