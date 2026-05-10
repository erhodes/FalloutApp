package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.model.Encounter

class EncounterRepository {
    var activeEncounter = Encounter("Test Encounter")

    init {
        // todo temporary test data
        activeEncounter.addCharacter(EnemyDataSource.createRaiderShotgunner())
        activeEncounter.addCharacter(EnemyDataSource.createRaiderPsycho())
        activeEncounter.addCharacter(EnemyDataSource.createRaiderLieutenant())
    }
}