package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.UserDataSource
import com.erhodes.falloutapp.data.localIdStore
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * The idea with this class is that it tracks the current status of the server connection.
 * It also handles requests to the server through the [UserDataSource].
 */
@OptIn(ExperimentalUuidApi::class)
class LoginRepository(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    val dataSource = UserDataSource()

    private val userIdReady = CompletableDeferred<String>()
    private val _loggedIn = MutableStateFlow(false)
    val loggedIn = _loggedIn.asStateFlow()

    private var serverAddress: String = ""

    init {
        scope.launch {
            var uuid = localIdStore.get() ?: ""
            if (uuid.isEmpty()) {
                uuid = Uuid.random().toString()
                localIdStore.set(uuid)
            }
            AppLogger.d("Eric", "UUID: $uuid")
            userIdReady.complete(uuid)
        }
    }

    suspend fun login(username: String, address: String) {
        val uuid = userIdReady.await()
        val success = dataSource.submitLoginRequest(User(uuid, username), address)
        if (success) {
            serverAddress = address
        }
        _loggedIn.value = success
    }

    suspend fun syncCharacters(characters: List<Character>): Boolean {
        return dataSource.syncCharacters(characters, serverAddress)
    }
}
