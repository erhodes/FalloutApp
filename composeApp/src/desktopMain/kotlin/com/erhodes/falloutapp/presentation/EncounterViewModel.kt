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

    private var activeEncounter = Encounter("Test Encounter")

    private val _activeEncounterState = MutableStateFlow(buildState())
    val activeEncounterState = _activeEncounterState.asStateFlow()

    init {
        // todo temporary until I set up a proper repository
        activeEncounter.addCharacter(EnemyDataSource.createRaiderShotgunner())
        activeEncounter.addCharacter(EnemyDataSource.createRaiderPsycho())
        activeEncounter.addCharacter(EnemyDataSource.createRaiderLieutenant())
        publishState()
    }

    fun onAddEnemy(type: EnemyEnum) {
        val newEnemy = when(type) {
            EnemyEnum.RAIDER_SHOTGUNNER -> EnemyDataSource.createRaiderShotgunner()
            EnemyEnum.RAIDER_PSYCHO -> EnemyDataSource.createRaiderPsycho()
        }
        activeEncounter.addCharacter(newEnemy)
        publishState()
    }

    fun onTakeDamage(enemyIndex: Int, amount: Int) {
        activeEncounter.characters.getOrNull(enemyIndex)?.let {
            it.takeDamage(amount)
            publishState()
        }
    }

    fun onHealDamage(enemyIndex: Int, amount: Int) {
        activeEncounter.characters.getOrNull(enemyIndex)?.let {
            it.healDamage(amount)
            publishState()
        }
    }

    fun onRepairArmor(enemyIndex: Int, amount: Int) {
        activeEncounter.characters.getOrNull(enemyIndex)?.let {
            it.repairArmor(amount)
            publishState()
        }
    }

    private fun buildState() = EncounterUiState(
        name = activeEncounter.name,
        enemies = activeEncounter.characters.mapIndexed { i, c -> EnemyUiState.from(i, c) }
    )

    private fun publishState() {
        scope.launch {
            _activeEncounterState.update { buildState() }
        }
    }
}
