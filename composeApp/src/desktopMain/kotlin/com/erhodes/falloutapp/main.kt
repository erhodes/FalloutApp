package com.erhodes.falloutapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.erhodes.falloutapp.di.initDesktopKoin
import com.erhodes.falloutapp.server.ServerManager

fun main() = application {
    initDesktopKoin()

    ServerManager().startServer()
//    val engine = startEmbeddedServer()
//
//    Runtime.getRuntime().addShutdownHook(Thread { engine.stop(1000, 2000) })

    Window(
        onCloseRequest = ::exitApplication,
        title = "Fallout Server"
    ) {
        ServerApp()
    }
}
