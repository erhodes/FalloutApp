package com.erhodes.falloutapp.model

import kotlinx.serialization.Serializable

@Serializable
class Encounter(
    val name: String
) {
    val characters: MutableList<Character> = mutableListOf()

    fun addCharacter(character: Character) {
        characters.add(character)
    }

    fun removeCharacter(index: Int) {
        if (index in characters.indices) {
            characters.removeAt(index)
        }
    }
}
