package com.erhodes.falloutapp.network

import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.model.campaign.Campaign
import com.erhodes.falloutapp.model.campaign.CampaignSummary
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.util.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserApi(private val httpClient: HttpClient) {

    suspend fun login(user: User): Boolean {
        AppLogger.d("Eric","sending login $user")
        val httpResponse = httpClient.post("/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        AppLogger.d("Eric"," got response $httpResponse")
        return httpResponse.status.value in 200..299
    }

    suspend fun syncCharacters(characters: List<PlayerCharacter>): List<PlayerCharacter> {
        AppLogger.d("Eric","sending characters ${characters.size}")

        val httpResponse = httpClient.post("/characters") {
            contentType(ContentType.Application.Json)
            setBody(characters)
        }
        AppLogger.d("Eric"," got response $httpResponse")
        if (httpResponse.status.value in 200..299) {
            val characters = httpResponse.body<List<PlayerCharacter>>()

            return characters
        } else {
            return ArrayList()
        }
    }

    suspend fun joinCampaign(character: PlayerCharacter): Boolean {
        AppLogger.d("Eric","joining campaign with ${character.name}")

        val httpResponse = httpClient.post("/campaign") {
            contentType(ContentType.Application.Json)
            setBody(character)
        }
        AppLogger.d("Eric"," got response $httpResponse")
        return httpResponse.status.value in 200..299
    }

    suspend fun getCampaignData(): Campaign {
        val httpResponse = httpClient.get("/campaign") {
            contentType(ContentType.Application.Json)
        }
        AppLogger.d("Eric"," got response $httpResponse")
        if (httpResponse.status.value in 200..299) {
            val campaign = httpResponse.body<CampaignSummary>()

            return Campaign(campaign.name, campaign.id)
        } else {
            return Campaign("ERROR", "")
        }
    }

    suspend fun getActiveEncounter(): Encounter {
        AppLogger.d("Eric","getting active encounter")

        val httpResponse = httpClient.get("/encounters") {
            contentType(ContentType.Application.Json)
        }

        AppLogger.d("Eric", "got response $httpResponse")
        if (httpResponse.status.value in 200..299) {
            val encounter = httpResponse.body<Encounter>()

            return encounter
        } else {
            return Encounter("ERROR")
        }
    }

    suspend fun getActiveLocation(): Location {
        AppLogger.d("Eric","getting active location")

        val httpResponse = httpClient.get("/campaign/location") {
            contentType(ContentType.Application.Json)
        }

        AppLogger.d("Eric", "got response $httpResponse")
        if (httpResponse.status.value in 200..299) {
            val location = httpResponse.body<Location>()

            return location
        } else {
            return Location("ERROR")
        }
    }
}
