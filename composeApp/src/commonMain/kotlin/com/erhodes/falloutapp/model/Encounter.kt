package com.erhodes.falloutapp.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
class Encounter(
    var name: String,
    val id: String = Uuid.random().toString()
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
