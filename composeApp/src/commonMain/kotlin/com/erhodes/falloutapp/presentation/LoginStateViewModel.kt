package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginStateViewModel : ViewModel(), KoinComponent {
    private val repo: LoginRepository by inject()

    private val _loginName = MutableStateFlow("")
    val loginName = _loginName.asStateFlow()

    fun login(name: String) {
        _loginName.value = name

        repo.login(name)
    }
}
