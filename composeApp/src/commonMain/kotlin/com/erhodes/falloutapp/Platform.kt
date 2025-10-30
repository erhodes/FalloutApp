package com.erhodes.falloutapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform