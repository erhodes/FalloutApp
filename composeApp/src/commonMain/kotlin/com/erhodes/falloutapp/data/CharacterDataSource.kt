package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CharacterDataSource(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    val kStore = store

    fun saveCharacters(characters: List<Character>) {
        val firstCharacter = SerializableCharacter.fromCharacter(characters[0])

        val string = Json.encodeToString(firstCharacter)

        scope.launch {
            kStore.set(string)
        }
    }

    suspend fun loadCharacters(): List<Character> {
        val result = ArrayList<Character>()
        val jsonString = kStore.get()

        if (!jsonString.isNullOrEmpty()) {
            val character = Json.decodeFromString<SerializableCharacter>(jsonString)
            result.add(character.toCharacter())
        }
        return result
    }

}