package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.network.UserApi
import com.erhodes.falloutapp.network.createHttpClient
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// This class is going to have some server calls in it. They might make more sense elsewhere.
class UserDataSource(private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)) {

    fun submitLoginRequest(user: User) {
        val client = createHttpClient()

        val userApi = UserApi(client)

        scope.launch {
            userApi.login(user)
        }
    }
}