package com.erhodes.falloutapp.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalDataSource(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    fun updateLoginInfo(username: String, address: String) {
        scope.launch {
            usernameStore.set(username)
            serverAddressStore.set(address)
        }
    }

    suspend fun getUsername(): String {
        return usernameStore.get() ?: ""
    }

    suspend fun getAddress(): String {
        return serverAddressStore.get() ?: ""
    }
}