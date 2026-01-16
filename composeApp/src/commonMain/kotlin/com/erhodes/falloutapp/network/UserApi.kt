package com.erhodes.falloutapp.network

import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.util.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserApi(private val httpClient: HttpClient) {
    suspend fun login(user: User) {
        AppLogger.d("Eric","sending login $user")
        val response = httpClient.post("/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        AppLogger.d("Eric"," got response ${response}")
    }
}