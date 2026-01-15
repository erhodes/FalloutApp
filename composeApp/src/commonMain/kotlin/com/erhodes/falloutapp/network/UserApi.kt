package com.erhodes.falloutapp.network

import com.erhodes.falloutapp.model.User
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class UserApi(private val httpClient: HttpClient) {
    suspend fun login(user: User) {
        httpClient.post()
    }
}