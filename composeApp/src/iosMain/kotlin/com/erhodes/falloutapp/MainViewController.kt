package com.erhodes.falloutapp

import androidx.compose.ui.window.ComposeUIViewController
import com.erhodes.falloutapp.di.initKoin

fun MainViewController(): androidx.compose.ui.platform.UIViewController {
	// Initialize DI for iOS
	initKoin()
	return ComposeUIViewController { App() }
}