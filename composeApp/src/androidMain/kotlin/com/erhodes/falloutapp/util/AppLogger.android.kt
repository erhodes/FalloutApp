package com.erhodes.falloutapp.util

import android.util.Log

actual object AppLogger {
    actual fun d(tag: String, message: String) {
        // commenting this out because it breaks unit tests
        Log.d(tag, message)
    }
}