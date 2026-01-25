package com.erhodes.falloutapp.network

import com.erhodes.falloutapp.data.ItemTemplateSerializer
import com.erhodes.falloutapp.data.PerkSerializer
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.util.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

class UserApi(private val httpClient: HttpClient) {

    //todo get items properly syncing
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

    suspend fun login(user: User): Boolean {
        AppLogger.d("Eric","sending login $user")
        val httpResponse = httpClient.post("/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        AppLogger.d("Eric"," got response $httpResponse")
        return httpResponse.status.value in 200..299
    }

    suspend fun syncCharacters(characters: List<Character>): List<Character> {
        AppLogger.d("Eric","sending characters ${characters.size}")

        val httpResponse = httpClient.post("/characters") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(characters))
        }
        AppLogger.d("Eric"," got response $httpResponse")
        if (httpResponse.status.value in 200..299) {
            val newCharacters = httpResponse.body<List<Character>>()
            return newCharacters
        } else {
            return ArrayList()
        }
    }
}
