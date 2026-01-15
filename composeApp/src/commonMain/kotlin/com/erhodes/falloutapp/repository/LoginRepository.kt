package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.UserDataSource

class LoginRepository {
    val dataSource = UserDataSource()

    fun login(username: String) {
        dataSource.submitLoginRequest(username)
    }
}