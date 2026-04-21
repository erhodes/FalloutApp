package com.erhodes.falloutapp.model

class Encounter(
    val name: String
) {
    val characters: MutableList<Character> = mutableListOf()

    fun addCharacter(character: Character) {
        characters.add(character)
    }
}