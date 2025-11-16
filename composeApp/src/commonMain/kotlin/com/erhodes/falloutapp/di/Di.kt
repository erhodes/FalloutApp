package com.erhodes.falloutapp.di

import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.ItemRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val appModule = module {
    single { CharacterRepository() }
    single { ItemRepository() }
}

/** Initialize Koin with the app module. Safe to call multiple times. */
fun initKoin() {
    try {
        startKoin {
            modules(appModule)
        }
    } catch (_: Throwable) {
        // ignore if already started
    }
}
