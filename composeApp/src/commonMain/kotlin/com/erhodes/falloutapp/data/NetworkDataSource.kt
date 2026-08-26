package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.model.campaign.Campaign
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.network.ClientApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// This class is going to have some server calls in it. They might make more sense elsewhere.
class NetworkDataSource(address: String) {

    var client: HttpClient
    var clientApi: ClientApi

    init {
        client = createHttpClient(address)
        clientApi = ClientApi(client)
    }

    fun newServerAddress(hostAddress: String) {
        client = createHttpClient(hostAddress)
        clientApi = ClientApi(client)
    }

    private fun createHttpClient(hostAddress: String) = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                isLenient = true
                coerceInputValues = true
                ignoreUnknownKeys = true
                serializersModule = DataManager.serializerModule
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 1000
        }
        defaultRequest {
            host = hostAddress
            port = 8080
        }
    }

    suspend fun submitLoginRequest(user: User): Boolean {
        return clientApi.login(user)
    }

    suspend fun syncCharacters(characters: List<PlayerCharacter>): List<PlayerCharacter> {
        return clientApi.syncCharacters(characters)
    }

    suspend fun joinCampaign(character: PlayerCharacter): Boolean {
        return clientApi.joinCampaign(character)
    }

    suspend fun getCampaignState(): Campaign {
        return clientApi.getCampaignData()
    }

    suspend fun getActiveEncounter(): Encounter {
        return clientApi.getActiveEncounter()
    }

    suspend fun getActiveLocation(): Location {
        return clientApi.getActiveLocation()
    }
}
