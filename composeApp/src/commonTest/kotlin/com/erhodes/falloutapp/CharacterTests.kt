package com.erhodes.falloutapp

import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.util.AppLogger
import kotlin.test.Test
import kotlin.test.assertEquals

class CharacterTests {

    @Test
    fun takeAndHealDamage() {
        val character = Character("Bob")
        assertEquals(0, character.damageTaken)
        character.takeDamage(3)
        assertEquals(3, character.damageTaken)
        character.healDamage(1)
        assertEquals(2, character.damageTaken)
    }

    @Test
    fun takeDamageWithArmor() {
        val character = Character("Bob")
        val armor = Armor(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ARMOR_LEATHER), 0)

        assertEquals(0, armor.damageTaken)
        assertEquals(0, character.damageTaken)

        character.addItemToInventory(armor)
        character.equipItem(armor)

        assertEquals(armor, character.equippedArmor)

        character.takeDamage(1)
        assertEquals(1, armor.damageTaken)
        assertEquals(0, character.damageTaken)

        character.takeDamage(4)
        assertEquals(3, armor.damageTaken)
        assertEquals(2, character.damageTaken)
    }

    @Test
    fun takeDamageWithToughArmor() {
        val character = Character("Bob")
        val armor = Armor(ItemDataSource.getItemTemplateById(ItemDataSource.ID_PA_RAIDER), 0)

        assertEquals(0, armor.damageTaken)
        assertEquals(0, character.damageTaken)

        character.addItemToInventory(armor)
        character.equipItem(armor)

        character.takeDamage(0)
        assertEquals(0, armor.damageTaken)
        assertEquals(0, character.damageTaken)

        AppLogger.d("Eric","toughness is ${armor.toughness}")

        character.takeDamage(1)
        assertEquals(0, armor.damageTaken)
        assertEquals(0, character.damageTaken)

        character.takeDamage(3)
        assertEquals(2, armor.damageTaken)
        assertEquals(0, character.damageTaken)

        character.takeDamage(4)
        assertEquals(4, armor.damageTaken)
        assertEquals(1, character.damageTaken)
    }

    @Test
    fun increaseRadiation() {
        val character = Character("Bob")

        assertEquals(0, character.getMinimumFatigue())

        character.modifyRadiation(11)
        assertEquals(11, character.radiation)
        assertEquals(1, character.fatigue)
        assertEquals(1, character.getMinimumFatigue())
    }

    @Test
    fun equipArmor() {
        val character = Character("Bob")
        val armor = Armor(ItemDataSource.getItemTemplateById(ItemDataSource.ID_PA_RAIDER), 0)

        assertEquals(0, character.load)
        assertEquals(0, character.inventoryWeight)
        character.addItemToInventory(armor)
        assertEquals(0, character.load)
        assertEquals(armor.load, character.inventoryWeight)
        character.equipItem(armor)
        assertEquals(1, character.load)
        assertEquals(0, character.inventoryWeight)
    }
}