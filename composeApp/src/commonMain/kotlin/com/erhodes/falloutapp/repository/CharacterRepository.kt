package com.erhodes.falloutapp.repository

import androidx.compose.runtime.mutableStateListOf
import com.erhodes.falloutapp.data.CharacterDataSource
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.Recipe
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.StackableItemTemplate
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.model.WeaponTemplate
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.model.condition.ScalableCondition
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

	val characters = mutableStateListOf<PlayerCharacter>()

    val remoteCharacters = mutableStateListOf<PlayerCharacter>()

    val dataSource = CharacterDataSource()

	init {
        scope.launch {
            characters.addAll(dataSource.loadCharacters())
        }
	}

	fun add(character: PlayerCharacter) {
		characters.add(character)
        saveCharacters()
	}

    fun addCharacters(newCharacters: List<PlayerCharacter>) {
        // only one character per player
        newCharacters.forEach { newChar ->
            characters.forEach { existingChar ->
                if (existingChar.ownerId == newChar.ownerId) {
                    characters.remove(existingChar)
                }
            }
            characters.add(newChar)
        }
        saveCharacters()
    }

    fun removeCharacter(character: PlayerCharacter) {
        characters.remove(character)
        saveCharacters()
    }

    fun setRemoteCharacters(characters: List<PlayerCharacter>) {
        remoteCharacters.clear()
        remoteCharacters.addAll(characters)
        // no local storage for remote characters at the moment
    }

    fun increaseSkillsForCharacter(increases: List<Int>, character: PlayerCharacter, milestone: Boolean) {
        for (i in 0 until increases.size) {
            character.skills[i]+=increases[i]
        }
        if (milestone) character.gainMilestone()
        saveCharacters()
    }

    fun addPerkToCharacter(perk: Perk, character: PlayerCharacter) {
        character.gainPerk(perk)
        saveCharacters()
    }

    fun removePerkFromCharacter(perk: Perk, character: PlayerCharacter) {
        character.removePerk(perk)
        saveCharacters()
    }

    fun addRecipeToCharacter(recipe: Recipe, character: PlayerCharacter) {
        if (character.learnRecipe(recipe)) {
            saveCharacters()
        }
    }

    fun removeRecipeFromCharacter(recipe: Recipe, character: PlayerCharacter) {
        character.recipes.remove(recipe)
        saveCharacters()
    }

    fun craftRecipeForCharacter(recipe: Recipe, character: PlayerCharacter): Boolean {
        val ok = character.consumeCraftingCost(recipe)
        if (ok) saveCharacters()
        return ok
    }

    fun increaseStackCountForCharacter(item: Item, count: Int, character: PlayerCharacter) {
        character.increaseStackCountForItem(item, count)
        saveCharacters()
    }

    fun decreaseStackCountForCharacter(item: Item, count: Int, character: PlayerCharacter) {
        character.decreaseStackCountForItem(item, count)
        saveCharacters()
    }

    fun addAmmoToWeapon(weapon: Weapon, count: Int, character: PlayerCharacter) {
        character.increaseStackCountForItem(weapon, count)
        saveCharacters()
    }

    fun removeAmmoFromWeapon(weapon: Weapon, count: Int, character: PlayerCharacter) {
        if (weapon.ammo - count < 0) return
        character.decreaseStackCountForItem(weapon, count)
        saveCharacters()
    }

    fun addNewItemToCharacter(newItem: ItemTemplate, character: PlayerCharacter) {
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

    fun removeItemFromCharacter(item: Item, character: PlayerCharacter) {
        character.removeItem(item)
        saveCharacters()
    }

    fun equipItemToCharacter(item: Item, character: PlayerCharacter) {
        character.equipItem(item)
        saveCharacters()
    }

    fun unequipItemFromCharacter(item: Item, character: PlayerCharacter) {
        character.unequipItem(item)
        saveCharacters()
    }

    fun damageCharacter(amount: Int, character: PlayerCharacter) {
        character.takeDamage(amount)
        saveCharacters()
    }

    fun healCharacter(amount: Int, character: PlayerCharacter) {
        character.healDamage(amount)
        saveCharacters()
    }

    fun repairArmorForCharacter(amount: Int, character: PlayerCharacter) {
        character.repairArmor(amount)
        saveCharacters()
    }

    fun modifyStressForCharacter(amount: Int, character: PlayerCharacter) {
        character.modifyStress(amount)
        saveCharacters()
    }

    fun modifyFatigueForCharacter(amount: Int, character: PlayerCharacter) {
        character.modifyFatigue(amount)
        saveCharacters()
    }

    fun modifyRadiationForCharacter(amount: Int, character: PlayerCharacter) {
        character.modifyRadiation(amount)
        saveCharacters()
    }

    fun modifyFearForCharacter(amount: Int, character: PlayerCharacter) {
        character.modifyFear(amount)
        saveCharacters()
    }

    fun modifyNameForCharacter(newName: String, character: PlayerCharacter) {
        character.name = newName
        saveCharacters()
    }

    fun addConditionToCharacter(condition: Condition, character: PlayerCharacter) {
        character.addCondition(condition)
        saveCharacters()
    }

    fun modifyConditionForCharacter(condition: Condition, amount: Int, character: PlayerCharacter) {
        if (condition is ScalableCondition) {
            condition.count+=amount
            if (condition.count < 1) {
                removeConditionFromCharacter(condition, character)
            }
        }
        saveCharacters()
    }

    fun removeConditionFromCharacter(condition: Condition, character: PlayerCharacter) {
        character.removeCondition(condition)
        saveCharacters()
    }

    private fun saveCharacters() {
        dataSource.saveCharacters(characters)
    }

}
