package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
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

    private val _activeLocationState = MutableStateFlow(buildState())
    val activeLocationState = _activeLocationState.asStateFlow()

    private val _savedLocations = MutableStateFlow<List<Location>>(emptyList())
    val savedLocations = _savedLocations.asStateFlow()

    fun onSelectLocation(location: Location) {
//        repo.setActiveEncounter(encounter)

        activeLocation = location
        publishState()
    }

    fun onNewLocation() {
        activeLocation = repo.createLocation()
        publishState()
    }

    fun onEditLocation(name: String, description: String) {
        activeLocation.name = name
        activeLocation.description = description
        publishState()
    }

    fun onSaveLocation() {
        // todo persist the active location. See EncounterViewModel.onSaveEncounter for an example
    }

    fun onSetActiveLocation(location: Location) {
        repo.setActiveLocation(location)
        activeLocation = location
        publishState()
    }

    fun loadSavedLocations() {
        scope.launch { _savedLocations.update { repo.loadAllLocations() } }
    }

    private fun buildState() = LocationUiState.from(activeLocation)

    private fun publishState() {
        scope.launch { _activeLocationState.update { buildState() } }
    }
}
