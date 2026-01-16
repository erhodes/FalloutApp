package com.erhodes.falloutapp.data

import android.content.Context
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

private lateinit var appContext: Context

fun initializeStorage(context: Context) {
    appContext = context.applicationContext
}

actual val store: KStore<String> by lazy {
    val filesDir = appContext.filesDir.absolutePath
    storeOf(
        file = Path("$filesDir/saved.json"),
        default = ""
    )
}
actual val localIdStore: KStore<String> by lazy {
    val filesDir = appContext.filesDir.absolutePath
    storeOf(
        file = Path("$filesDir/localId.json"),
        default = ""
    )
}