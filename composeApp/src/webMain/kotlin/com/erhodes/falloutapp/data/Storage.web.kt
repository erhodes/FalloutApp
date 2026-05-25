package com.erhodes.falloutapp.data

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf

actual val store: KStore<String> by lazy {
    storeOf(
        key = "saved",
        default = ""
    )
}

actual val localIdStore: KStore<String> by lazy {
    storeOf(
        key = "saved",
        default = ""
    )
}

actual val usernameStore: KStore<String> by lazy {
    storeOf(
        key = "username",
        default = ""
    )
}

actual val serverAddressStore: KStore<String> by lazy {
    storeOf(
        key = "serverAddress",
        default = ""
    )
}