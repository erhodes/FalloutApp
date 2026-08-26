package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.repository.RemoteLocationRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoteLocationViewModel: ViewModel(), KoinComponent {
    private val repo: RemoteLocationRepository by inject()

    val activeLocationState = repo.activeLocation
}