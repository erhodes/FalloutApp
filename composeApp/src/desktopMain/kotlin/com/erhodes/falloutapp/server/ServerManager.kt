package com.erhodes.falloutapp.server

import com.erhodes.falloutapp.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ServerManager: KoinComponent {
    val userRepository: UserRepository by inject()

    fun startServer() {
        println("Server started")

        val engine = startEmbeddedServer(userRepository)

        Runtime.getRuntime().addShutdownHook(Thread { engine.stop(1000, 2000) })
    }


}