package com.erhodes.falloutapp.data

import io.github.xxfast.kstore.KStore

expect val store: KStore<String>

expect val localIdStore: KStore<String>