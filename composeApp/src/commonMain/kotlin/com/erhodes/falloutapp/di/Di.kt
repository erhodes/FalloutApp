package com.erhodes.falloutapp.di

import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.ItemRepository
import com.erhodes.falloutapp.repository.LoginRepository
import com.erhodes.falloutapp.repository.PerkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { CoroutineScope(Dispatchers.Default) }
    single { CharacterRepository(get()) }
    single { ItemRepository() }
    single { PerkRepository() }
    single { LoginRepository(get(), get()) }
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
