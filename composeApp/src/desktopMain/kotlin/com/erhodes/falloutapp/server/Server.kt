package com.erhodes.falloutapp.server

import com.erhodes.falloutapp.repository.UserRepository
import com.erhodes.falloutapp.util.AppLogger
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class HealthStatus(val status: String = "ok")

fun Application.falloutModule(userRepository: UserRepository) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/health") {
            call.respond(HealthStatus())
        }
        get("/characters") {
            call.respond("Work in progress")
        }
        route("/users") {
            post {
                val string = call.receive<String>()
                AppLogger.d("Eric", "new user $string")
                userRepository.addUser(string)
                call.respond("success")
            }
        }
    }
}

fun startEmbeddedServer(userRepository: UserRepository, port: Int = 8080): EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration> {
    return embeddedServer(Netty, port = port, host = "0.0.0.0") {
        falloutModule(userRepository)
    }.start(wait = false)
}
