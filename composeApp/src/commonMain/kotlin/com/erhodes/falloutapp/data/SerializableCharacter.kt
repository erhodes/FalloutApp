package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Character
import kotlinx.serialization.Serializable

@Serializable
class SerializableCharacter(val name: String) {

    companion object {
        fun fromCharacter(character: Character): SerializableCharacter {
            return SerializableCharacter(character.name)
        }
    }

    fun toCharacter(): Character {
        return Character(this.name)
    }

}