package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {
    private val _loginName = MutableStateFlow("")
    val loginName = _loginName.asStateFlow()

    fun login(name: String) {
        _loginName.value = name
    }
}
