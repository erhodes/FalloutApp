package com.erhodes.falloutapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.erhodes.falloutapp.di.initKoin
import com.erhodes.falloutapp.server.startEmbeddedServer

fun main() = application {
    initKoin()

    val engine = startEmbeddedServer()
    Runtime.getRuntime().addShutdownHook(Thread { engine.stop(1000, 2000) })

    Window(
        onCloseRequest = ::exitApplication,
        title = "FalloutApp"
    ) {
        App()
    }
}
