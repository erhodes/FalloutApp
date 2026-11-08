package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.campaign.BasicFeature
import com.erhodes.falloutapp.model.campaign.BasicLocation
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Store
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
        activeLocation = BasicLocation("New Location")
        publishState()
    }

    /**
     * Applies the edit and persists it. The edit dialog is the only thing that mutates a location,
     * so committing here means the screen can never show unsaved changes.
     */
    fun onEditLocation(name: String, description: String) {
        activeLocation.name = name
        activeLocation.description = description
        commit()
    }

    /** Adds a feature of the chosen [FeatureType] to the active location. */
    fun onAddFeature(type: FeatureType, name: String, description: String) {
        val feature = when (type) {
            FeatureType.BASIC -> BasicFeature(name = name, description = description)
            FeatureType.STORE -> Store(name = name, description = description)
        }
        activeLocation.features.add(feature)
        commit()
    }

    fun onRemoveFeature(id: String) {
        activeLocation.features.removeAll { it.id == id }
        commit()
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

    /**
     * Republishes the active location and writes it through the repository. Every mutation goes
     * through here so a change can never be shown without being persisted.
     */
    private fun commit() {
        publishState()
        scope.launch {
            repo.saveLocation(activeLocation)
            // Refresh in the same coroutine so the list can't be re-read before the write commits.
            _savedLocations.update { repo.loadAllLocations() }
        }
    }
}
