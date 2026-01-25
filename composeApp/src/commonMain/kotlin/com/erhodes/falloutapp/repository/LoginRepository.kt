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
    private val characterRepository: CharacterRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    val dataSource = UserDataSource(characterRepository)

    var userId: String = ""
        private set

    // the issue with using the CompletableDeferred is it means getting the id is a suspend, which isn't great.
    // But without it theres the possibility of a race condition so I'm leaving it here as a reminder
    //    private val userIdReady = CompletableDeferred<String>()

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
            userId = uuid
            AppLogger.d("Eric", "UUID: $userId")
//            userIdReady.complete(uuid)
        }
    }

    suspend fun login(username: String, address: String) {
//        val uuid = userIdReady.await()
        val success = dataSource.submitLoginRequest(User(userId, username), address)
        if (success) {
            serverAddress = address
        }
        _loggedIn.value = success
    }

    suspend fun syncCharacters(characters: List<Character>) {
        val remoteCharacters = dataSource.syncCharacters(characters, serverAddress)
        // any characters we own should be excluded as they are not remote
        val filteredList = remoteCharacters.filter { it.ownerId != userId }
        if (filteredList.isNotEmpty()) {
            characterRepository.setRemoteCharacters(filteredList)
        }
    }
}
