package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.EnemyEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EncounterViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel() {

    private var activeEncounter = Encounter("empty")

    private val _activeEncounterState = MutableStateFlow<EncounterUiState>(EncounterUiState(activeEncounter))
    val activeEncounterState = _activeEncounterState.asStateFlow()

//    private val _encounter = MutableStateFlow<Encounter?>(null)
//    val encounter: StateFlow<Encounter?> = _encounter

    init {
        // todo temporary until I set up a proper repository
        val enc = Encounter("Test Encounter")
        enc.addCharacter(EnemyDataSource.createRaiderShotgunner())
        enc.addCharacter(EnemyDataSource.createRaiderPsycho())

        updateActiveEncounter()
    }

    fun onAddEnemy(type: EnemyEnum) {
        val newEnemy = when(type) {
            EnemyEnum.RAIDER_SHOTGUNNER -> EnemyDataSource.createRaiderShotgunner()
            EnemyEnum.RAIDER_PSYCHO -> EnemyDataSource.createRaiderPsycho()
        }
        activeEncounter.addCharacter(newEnemy)
        updateActiveEncounter()
    }

    private fun updateActiveEncounter() {
        scope.launch {
            _activeEncounterState.update { EncounterUiState(activeEncounter) }
        }
    }
}