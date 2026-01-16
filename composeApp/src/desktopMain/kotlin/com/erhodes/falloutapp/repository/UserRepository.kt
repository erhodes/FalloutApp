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

    fun addUser(name: String) {
        val newUser = User((_users.value.size + 1).toString(), name)
        _users.update { it + newUser }
    }
}
