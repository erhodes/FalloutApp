package com.erhodes.falloutapp.model

class Encounter(
    val name: String
) {
    val characters: MutableList<Enemy> = mutableListOf()

    fun addCharacter(character: Enemy) {
        characters.add(character)
    }

    fun removeCharacter(index: Int) {
        if (index in characters.indices) {
            characters.removeAt(index)
        }
    }
}
