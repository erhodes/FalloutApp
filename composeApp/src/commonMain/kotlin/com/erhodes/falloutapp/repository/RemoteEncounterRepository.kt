package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.util.AppLogger

class RemoteEncounterRepository {

    fun setActiveEncounter(encounter: Encounter) {
        AppLogger.d("Eric", "got active encounte ${encounter.name} with ${encounter.characters.size} characters")
    }
}