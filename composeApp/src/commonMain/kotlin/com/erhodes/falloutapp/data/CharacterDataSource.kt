package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

class CharacterDataSource(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    val kStore = store

    val json: Json = Json {
        serializersModule = SerializersModule {
            contextual(ItemTemplateSerializer)
            contextual(PerkSerializer)
            polymorphic(Item::class){
                subclass(BasicItem::class)
                subclass(Armor::class)
                subclass(StackableItem::class)
                subclass(Weapon::class)
            }
        }
    }

    fun saveCharacters(characters: List<Character>) {
        val string = json.encodeToString(characters)

        AppLogger.d("Eric","saving json $string")
        scope.launch {
            kStore.set(string)
        }
    }

    suspend fun loadCharacters(): List<Character> {
        val result = ArrayList<Character>()
        val jsonString = kStore.get()

        AppLogger.d("Eric","loading json $jsonString")
        if (!jsonString.isNullOrEmpty()) {
            val characters = json.decodeFromString<List<Character>>(jsonString)
            result.addAll(characters)
        }
        return result
    }

}