package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.Character

/**
 * Simple in-memory repository for Characters.
 *
 * This repository is intentionally small and platform-agnostic (commonMain).
 * It provides basic CRUD operations and a lightweight subscription callback
 * mechanism suitable for MVVM consumers in Compose.
 */
class CharacterRepository {

	val characters = mutableListOf<Character>()
	private val listeners = mutableSetOf<(List<Character>) -> Unit>()

	init {
		// Seed with a couple of sample characters to make UI previews easier.
		characters.add(Character("Vault Dweller"))
		characters.add(Character("Wanderer"))
	}

	/** Return a snapshot of all characters. */
	fun getAll(): List<Character> = characters.toList()

	/** Add a character instance. Notifies subscribers. */
	fun add(character: Character) {
		characters.add(character)
		notifyListeners()
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