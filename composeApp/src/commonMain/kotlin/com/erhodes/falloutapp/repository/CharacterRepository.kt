package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.CharacterDataSource
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.ArmorTemplate
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.Perk
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

	val characters = mutableListOf<Character>()
	private val listeners = mutableSetOf<(List<Character>) -> Unit>()

    val dataSource = CharacterDataSource()

	init {
		// Seed with a couple of sample characters to make UI previews easier.
//		characters.add(Character("Vault Dweller"))
//		characters.add(Character("Wanderer"))

        scope.launch {
            characters.addAll(dataSource.loadCharacters())
        }

	}

	/** Return a snapshot of all characters. */
	fun getAll(): List<Character> = characters.toList()

	/** Add a character instance. Notifies subscribers. */
	fun add(character: Character) {
		characters.add(character)
		notifyListeners()

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

    fun addNewItemToCharacter(newItem: ItemTemplate, character: Character) {
        if (newItem is ArmorTemplate) {
            character.addItemToInventory(Armor(newItem, 0))
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

    private fun saveCharacters() {
        dataSource.saveCharacters(characters)
    }

	/** Convenience: add by name. */
	fun add(name: String) = add(Character(name))

	/** Remove characters matching [name]. Returns true if anything was removed. */
	fun removeByName(name: String): Boolean {
		val removed = characters.removeAll { it.name == name }
		if (removed) notifyListeners()
		return removed
	}

	/** Update the first character with [oldName] to have [newName]. */
	fun updateName(oldName: String, newName: String): Boolean {
		val idx = characters.indexOfFirst { it.name == oldName }
		return if (idx >= 0) {
			characters[idx] = Character(newName)
			notifyListeners()
			true
		} else {
			false
		}
	}

	/** Remove all characters. */
	fun clear() {
		if (characters.isNotEmpty()) {
			characters.clear()
			notifyListeners()
		}
	}

	/**
	 * Subscribe to changes. The subscriber will immediately receive the current
	 * snapshot and then be called on subsequent changes.
	 *
	 * Returns an unsubscribe function which the caller should invoke to stop
	 * receiving updates.
	 */
	fun subscribe(listener: (List<Character>) -> Unit): () -> Unit {
		listeners.add(listener)
		// deliver current state right away
		listener(getAll())
		return { listeners.remove(listener) }
	}

	private fun notifyListeners() {
		val snapshot = getAll()
		// copy listeners to avoid concurrent-modification if a listener unsubscribes
		val copy = listeners.toList()
		copy.forEach { it(snapshot) }
	}

}