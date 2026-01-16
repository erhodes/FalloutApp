package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserViewModel: ViewModel(), KoinComponent {
    private val repository: UserRepository by inject()
    val users = repository.users
}
