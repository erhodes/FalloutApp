package com.erhodes.falloutapp.network

import com.erhodes.falloutapp.util.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserApi(private val httpClient: HttpClient) {
    suspend fun login(name: String) {
        AppLogger.d("Eric","sending login $name")
        val response = httpClient.post("/users") {
            contentType(ContentType.Application.Json)
            setBody(name)
        }
        AppLogger.d("Eric"," got response ${response}")
    }
}