package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject

class CharacterDataSource(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    val kStore = store

    val json: Json = Json {
        serializersModule = DataManager.serializerModule
        ignoreUnknownKeys = true
        classDiscriminator = "type"
    }

    fun saveCharacters(characters: List<PlayerCharacter>) {
        val string = json.encodeToString(characters)

        scope.launch {
            kStore.set(string)
        }
    }

    suspend fun loadCharacters(): List<PlayerCharacter> {
        val result = ArrayList<PlayerCharacter>()
        val jsonString = kStore.get()

        if (!jsonString.isNullOrEmpty()) {
            try {
                val migrated = injectPlayerDiscriminator(jsonString)
                val characters = json.decodeFromString<List<PlayerCharacter>>(migrated)
                result.addAll(characters)
            } catch (e: Exception) {
                AppLogger.d("CharacterDataSource", "Failed to load characters: ${e.message}")
            }
        }
        return result
    }

    /**
     * Pre-refactor saved characters have no "type" discriminator. Inject "type": "player"
     * into any element that lacks it so kotlinx-serialization can resolve the sealed class.
     */
    private fun injectPlayerDiscriminator(jsonString: String): String {
        val element = json.parseToJsonElement(jsonString)
        if (element !is JsonArray) return jsonString
        val patched = buildJsonArray {
            element.forEach { item ->
                if (item is JsonObject && !item.containsKey("type")) {
                    add(buildJsonObject {
                        put("type", JsonPrimitive("player"))
                        item.entries.forEach { (k, v) -> put(k, v) }
                    })
                } else {
                    add(item)
                }
            }
        }
        return json.encodeToString(JsonArray.serializer(), patched)
    }
}
