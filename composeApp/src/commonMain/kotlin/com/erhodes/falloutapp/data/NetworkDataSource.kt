package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.model.campaign.Campaign
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.network.UserApi
import com.erhodes.falloutapp.network.createHttpClient
import com.erhodes.falloutapp.repository.CharacterRepository

// This class is going to have some server calls in it. They might make more sense elsewhere.
class NetworkDataSource(private val characterRepository: CharacterRepository) {

    suspend fun submitLoginRequest(user: User, address: String): Boolean {
        val client = createHttpClient(address)

        val userApi = UserApi(client)

        return userApi.login(user)
    }

    suspend fun syncCharacters(characters: List<PlayerCharacter>, address: String): List<PlayerCharacter> {
        val client = createHttpClient(address)
        val userApi = UserApi(client)
        return userApi.syncCharacters(characters)
    }

    suspend fun joinCampaign(character: PlayerCharacter, address: String): Boolean {
        val client = createHttpClient(address)
        val userApi = UserApi(client)
        return userApi.joinCampaign(character)
    }

    suspend fun getCampaignState(address: String): Campaign {
        val client = createHttpClient(address)
        val userApi = UserApi(client)
        return userApi.getCampaignData()
    }

    suspend fun getActiveEncounter(address: String): Encounter {
        val client = createHttpClient(address)
        val userApi = UserApi(client)
        return userApi.getActiveEncounter()
    }

    suspend fun getActiveLocation(address: String): Location {
        val client = createHttpClient(address)
        val userApi = UserApi(client)
        return userApi.getActiveLocation()
    }
}
