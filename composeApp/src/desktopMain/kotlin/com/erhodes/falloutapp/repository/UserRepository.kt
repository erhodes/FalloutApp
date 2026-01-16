package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserRepository {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    init {
        _users.value = listOf(
            User("1", "Alice"),
            User("2", "Bob")
        )
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
    }

    private fun findUserById(uuid: String): User? {
        return _users.value.find { it.uuid == uuid }
    }
}
