package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.network.UserApi
import com.erhodes.falloutapp.network.createHttpClient
import com.erhodes.falloutapp.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// This class is going to have some server calls in it. They might make more sense elsewhere.
class UserDataSource(private val characterRepository: CharacterRepository) {

    suspend fun submitLoginRequest(user: User, address: String): Boolean {
        val client = createHttpClient(address)

        val userApi = UserApi(client)

        return userApi.login(user)
    }

    suspend fun syncCharacters(characters: List<Character>, address: String): List<Character> {
        val client = createHttpClient(address)
        val userApi = UserApi(client)
        return userApi.syncCharacters(characters)
    }
}
