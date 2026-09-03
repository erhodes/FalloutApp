package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RemoteLocationRepository {

    // there's an alternative approach in the RemoteEncounterRepository
    private val _activeLocation = MutableStateFlow(Location(-1, "PENDING"))
    val activeLocation = _activeLocation.asStateFlow()

    fun setActiveLocation(location: Location) {
        AppLogger.d("Eric", "got active location ${location.name}")
        _activeLocation.value = location
    }
}
