package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erhodes.falloutapp.repository.RemoteLocationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoteLocationViewModel: ViewModel(), KoinComponent {
    private val repo: RemoteLocationRepository by inject()

    val activeLocationState = repo.activeLocation
        .map { LocationUiState.from(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LocationUiState.EMPTY)
}
