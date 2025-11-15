package com.erhodes.falloutapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.erhodes.falloutapp.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Initialize DI
    initKoin()
    ComposeViewport {
        App()
    }
}