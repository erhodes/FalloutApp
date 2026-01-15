package com.erhodes.falloutapp.di

import com.erhodes.falloutapp.repository.UserRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val desktopModule = module {
    single { UserRepository() }
}

/**
 * Initialize Koin with desktop-specific and common dependencies. Safe to call multiple times.
 */
fun initDesktopKoin() {
    // We deliberately re-start Koin with both modules; startKoin is idempotent-safe via try/catch
    runCatching {
        startKoin {
            modules(appModule, desktopModule)
        }
    }
}
