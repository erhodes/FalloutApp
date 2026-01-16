package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.UserDataSource
import com.erhodes.falloutapp.data.localIdStore
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class LoginRepository(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    val dataSource = UserDataSource()

    init {
        scope.launch {
            var uuid = localIdStore.get()
            if (uuid.isNullOrEmpty()) {
                uuid = Uuid.random().toString()
                localIdStore.set(uuid)
            }
            AppLogger.d("Eric", "UUID: $uuid")
        }
    }

    fun login(username: String) {
        dataSource.submitLoginRequest(username)
    }
}