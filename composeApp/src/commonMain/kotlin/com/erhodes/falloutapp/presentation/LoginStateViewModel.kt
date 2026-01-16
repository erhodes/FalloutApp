package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginStateViewModel : ViewModel(), KoinComponent {
    private val repo: LoginRepository by inject()
    private val characterRepo: CharacterRepository by inject()

    val loginState = repo.loggedIn

    fun login(name: String, address: String) {
        viewModelScope.launch {
            repo.login(name, address)
        }
    }

    fun sync() {
        viewModelScope.launch {
            val characters = characterRepo.characters
            repo.syncCharacters(characters)
        }
    }
}
