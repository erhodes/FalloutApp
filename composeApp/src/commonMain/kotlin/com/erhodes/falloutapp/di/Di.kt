package com.erhodes.falloutapp.di

import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.ItemRepository
import com.erhodes.falloutapp.repository.LoginRepository
import com.erhodes.falloutapp.repository.PerkRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { CharacterRepository() }
    single { ItemRepository() }
    single { PerkRepository() }
    single { LoginRepository() }
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
