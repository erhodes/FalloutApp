package com.erhodes.falloutapp.repository

import androidx.compose.runtime.mutableStateListOf
import com.erhodes.falloutapp.data.CharacterDataSource
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.StackableItemTemplate
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.model.WeaponTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Simple in-memory repository for Characters.
 *
 * This repository is intentionally small and platform-agnostic (commonMain).
 * It provides basic CRUD operations and a lightweight subscription callback
 * mechanism suitable for MVVM consumers in Compose.
 */
class CharacterRepository(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

	val characters = mutableStateListOf<Character>()

    val dataSource = CharacterDataSource()

	init {
		// Seed with a couple of sample characters to make UI previews easier.
//		characters.add(Character("Vault Dweller"))
//		characters.add(Character("Wanderer"))

        scope.launch {
            characters.addAll(dataSource.loadCharacters())
        }

	}

	fun add(character: Character) {
		characters.add(character)
        saveCharacters()
	}

    fun removeCharacter(character: Character) {
        characters.remove(character)
        saveCharacters()
    }

    fun increaseSkillsForCharacter(increases: List<Int>, character: Character, milestone: Boolean) {
        for (i in 0 until increases.size) {
            character.skills[i]+=increases[i]
        }
        if (milestone) character.gainMilestone()
        saveCharacters()
    }

    fun addPerkToCharacter(perk: Perk, character: Character) {
        character.gainPerk(perk)
        saveCharacters()
    }

    fun removePerkFromCharacter(perk: Perk, character: Character) {
        character.removePerk(perk)
        saveCharacters()
    }

    fun increaseStackCountForCharacter(item: Item, count: Int, character: Character) {
        character.increaseStackCountForItem(item, count)
        saveCharacters()
    }

    fun decreaseStackCountForCharacter(item: Item, count: Int, character: Character) {
        character.decreaseStackCountForItem(item, count)
        saveCharacters()
    }

    fun addAmmoToWeapon(weapon: Weapon, count: Int) {
        if (weapon.ammo + count > weapon.magazineSize) return
        weapon.ammo += count
        saveCharacters()
    }

    fun removeAmmoFromWeapon(weapon: Weapon, count: Int) {
        if (weapon.ammo - count < 0) return
        weapon.ammo -= count
        saveCharacters()
    }

    fun addNewItemToCharacter(newItem: ItemTemplate, character: Character) {
        if (newItem is ArmorTemplate) {
            character.addItemToInventory(Armor(newItem, 0))
        } else if (newItem is WeaponTemplate) {
            character.addItemToInventory(Weapon(newItem, 0))
        } else if (newItem is StackableItemTemplate) {
            character.addItemToInventory(StackableItem(newItem, 1))
        } else {
            character.addItemToInventory(BasicItem(newItem))
        }

        saveCharacters()
    }

    fun removeItemFromCharacter(item: Item, character: Character) {
        character.removeItemFromInventory(item)
        saveCharacters()
    }

    fun equipItemToCharacter(item: Item, character: Character) {
        character.equipItem(item)
        saveCharacters()
    }

    fun unequipItemFromCharacter(item: Item, character: Character) {
        character.unequipItem(item)
        saveCharacters()
    }

    fun damageCharacter(amount: Int, character: Character) {
        character.takeDamage(amount)
        saveCharacters()
    }

    fun healCharacter(amount: Int, character: Character) {
        character.healDamage(amount)
        saveCharacters()
    }

    fun repairArmorForCharacter(amount: Int, character: Character) {
        character.repairArmor(amount)
        saveCharacters()
    }

    fun modifyStressForCharacter(amount: Int, character: Character) {
        character.modifyStress(amount)
        saveCharacters()
    }

    fun modifyFatigueForCharacter(amount: Int, character: Character) {
        character.modifyFatigue(amount)
        saveCharacters()
    }

    fun modifyRadiationForCharacter(amount: Int, character: Character) {
        character.modifyRadiation(amount)
        saveCharacters()
    }

    private fun saveCharacters() {
        dataSource.saveCharacters(characters)
    }

}