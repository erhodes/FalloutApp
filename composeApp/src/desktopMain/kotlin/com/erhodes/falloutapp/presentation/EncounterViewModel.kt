package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.EnemyEnum
import com.erhodes.falloutapp.repository.EncounterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EncounterViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel(), KoinComponent {
    private val repo: EncounterRepository by inject()

    private var activeEncounter = Encounter("Test Encounter")

    private val _activeEncounterState = MutableStateFlow(buildState())
    val activeEncounterState = _activeEncounterState.asStateFlow()

    init {
        activeEncounter = repo.activeEncounter
        publishState()
    }

    fun onAddEnemy(type: EnemyEnum) {
        val newEnemy = when(type) {
            EnemyEnum.RAIDER_SHOTGUNNER -> EnemyDataSource.createRaiderShotgunner()
            EnemyEnum.RAIDER_PSYCHO -> EnemyDataSource.createRaiderPsycho()
            EnemyEnum.RAIDER_LIEUTENANT -> EnemyDataSource.createRaiderLieutenant()
            EnemyEnum.RAIDER_HEAVY -> EnemyDataSource.createRaiderHeavy()
            EnemyEnum.RAIDER_STALKER -> EnemyDataSource.createRaiderStalker()
            EnemyEnum.ZAPPER -> EnemyDataSource.createZapper()
            EnemyEnum.GHOUL -> EnemyDataSource.createGhoul()
            EnemyEnum.PROTECTRON -> EnemyDataSource.createProtectron()
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

    fun onRemoveEnemy(enemyIndex: Int) {
        activeEncounter.removeCharacter(enemyIndex)
        publishState()
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
