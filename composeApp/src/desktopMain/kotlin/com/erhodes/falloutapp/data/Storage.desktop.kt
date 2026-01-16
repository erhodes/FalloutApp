package com.erhodes.falloutapp.data

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

actual val store: KStore<String> by lazy {
    val homeDir = System.getProperty("user.home")
    val appDir = Path("$homeDir/.falloutapp")
    
    // Ensure the directory exists
    if (!SystemFileSystem.exists(appDir)) {
        SystemFileSystem.createDirectories(appDir)
    }
    
    storeOf(
        file = Path("$homeDir/.falloutapp/saved.json"),
        default = ""
    )
}
actual val localIdStore: KStore<String> by lazy {
    val homeDir = System.getProperty("user.home")
    val appDir = Path("$homeDir/.falloutapp")

    // Ensure the directory exists
    if (!SystemFileSystem.exists(appDir)) {
        SystemFileSystem.createDirectories(appDir)
    }

    storeOf(
        file = Path("$homeDir/.falloutapp/localId.json"),
        default = ""
    )
}