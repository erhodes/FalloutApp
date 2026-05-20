package com.erhodes.falloutapp.data

import io.github.xxfast.kstore.KStore

expect val store: KStore<String>

expect val localIdStore: KStore<String>

expect val usernameStore: KStore<String>

expect val serverAddressStore: KStore<String>