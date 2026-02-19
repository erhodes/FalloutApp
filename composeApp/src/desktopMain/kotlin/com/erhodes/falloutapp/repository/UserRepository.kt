package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.localUserStore
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.collections.ArrayList

class UserRepository(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private val userKstore = localUserStore


    init {
        scope.launch {
            _users.value = loadUsers()
        }
    }

    fun addUser(user: User) {
        val existingUser = findUserById(user.uuid)
        if (existingUser == null) {
            _users.update { it + user }
        } else {
            _users.update { userList ->
                userList.map {
                    if (it.uuid == user.uuid) {
                        user
                    } else {
                        it
                    }
                }
            }
        }
        saveUsers(_users.value)
    }

    private fun findUserById(uuid: String): User? {
        return _users.value.find { it.uuid == uuid }
    }

    //todo put these in a data source
    fun saveUsers(characters: List<User>) {
        val string = Json.encodeToString(characters)

        AppLogger.d("Eric","saving json $string")
        scope.launch {
            userKstore.set(string)
        }
    }

    suspend fun loadUsers(): List<User> {
        val result = ArrayList<User>()
        val jsonString = userKstore.get()

        AppLogger.d("Eric","loading json $jsonString")
        if (!jsonString.isNullOrEmpty()) {
            val users = Json.decodeFromString<List<User>>(jsonString)
            result.addAll(users)
        }
        return result
    }
}
