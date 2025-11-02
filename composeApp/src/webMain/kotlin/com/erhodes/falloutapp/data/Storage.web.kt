package com.erhodes.falloutapp.data

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf

actual val store: KStore<String> by lazy {
    storeOf(
        key = "saved",
        default = ""
    )
}