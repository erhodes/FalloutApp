package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

class CharacterDataSource(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    val kStore = store

    val json: Json = Json {
        serializersModule = SerializersModule {
            contextual(ItemTemplateSerializer)
        }
    }

    fun saveCharacters(characters: List<Character>) {
        val string = json.encodeToString(characters)

        AppLogger.d("Eric","json is $string")
        scope.launch {
            kStore.set(string)
        }
    }

    suspend fun loadCharacters(): List<Character> {
        val result = ArrayList<Character>()
        val jsonString = kStore.get()

        AppLogger.d("Eric","read in $jsonString")
        if (!jsonString.isNullOrEmpty()) {
            val characters = json.decodeFromString<List<Character>>(jsonString)
            result.addAll(characters)
        }
        return result
    }

}