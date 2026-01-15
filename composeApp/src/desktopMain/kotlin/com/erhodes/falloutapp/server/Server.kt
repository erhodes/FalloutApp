package com.erhodes.falloutapp.server

import com.erhodes.falloutapp.util.AppLogger
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class HealthStatus(val status: String = "ok")

fun Application.falloutModule() {
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
                AppLogger.d("Eric","new user $call")
            }
        }
    }
}

fun startEmbeddedServer(port: Int = 8080): EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration> {
    return embeddedServer(Netty, port = port, host = "0.0.0.0") {
        falloutModule()
    }.start(wait = false)
}
