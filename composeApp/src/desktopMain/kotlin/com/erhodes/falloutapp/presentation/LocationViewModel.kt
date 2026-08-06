package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class LocationViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel(), KoinComponent {
    private val repo: LocationRepository by inject()

    private var activeLocation: Location = repo.activeLocation

    //todo finish building this out. See EncounterViewModel for an example
    private val _activeLocationState = MutableStateFlow(Location(""))
    val activeLocationState = _activeLocationState.asStateFlow()

    private val _savedLocations = MutableStateFlow<List<Location>>(emptyList())
    val savedLocations = _savedLocations.asStateFlow()

    fun onSelectLocation(location: Location) {
//        repo.setActiveEncounter(encounter)

        activeLocation = location
        scope.launch { _activeLocationState.update { activeLocation } }
//        activeEncounter = encounter // keep the VM's field in sync with the repo swap
//        publishState()
    }

    fun loadSavedLocations() {
        scope.launch { _savedLocations.update { repo.loadAllLocations() } }
    }
}