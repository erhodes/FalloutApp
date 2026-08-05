package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Holds the encounter most recently fetched from the server. The client only ever observes this
 * encounter, the server owns it.
 */
class RemoteEncounterRepository {

    private val _activeEncounter = MutableStateFlow<Encounter?>(null)
    val activeEncounter = _activeEncounter.asStateFlow()

    fun setActiveEncounter(encounter: Encounter) {
        AppLogger.d("Eric", "got active encounte ${encounter.name} with ${encounter.characters.size} characters")
        _activeEncounter.value = encounter
    }
}
