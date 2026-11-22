package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.EnemyTemplate
import com.erhodes.falloutapp.model.condition.Condition
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

    private var activeEncounter: Encounter = repo.activeEncounter

    private val _activeEncounterState = MutableStateFlow(buildState())
    val activeEncounterState = _activeEncounterState.asStateFlow()

    private val _savedEncounters = MutableStateFlow<List<Encounter>>(emptyList())
    val savedEncounters = _savedEncounters.asStateFlow()

    init {
        publishState() // eager initial state; the collector below runs async
        scope.launch {
            repo.changes.collect { publishState() }
        }
    }

    fun onAddEnemy(template: EnemyTemplate) = repo.addCharacterToEncounter(template.create())

    fun loadSavedEncounters() {
        scope.launch { _savedEncounters.update { repo.loadAllEncounters() } }
    }

    fun onSelectEncounter(encounter: Encounter) {
        repo.setActiveEncounter(encounter)
        activeEncounter = encounter // keep the VM's field in sync with the repo swap
        publishState()
    }

    fun onNewEncounter() {
        val encounter = Encounter("New Encounter")
        repo.setActiveEncounter(encounter)
        activeEncounter = encounter // keep the VM's field in sync with the repo swap
        publishState()
    }

    fun onSaveEncounter() = repo.saveActiveEncounter()

    fun onTakeDamage(enemyIndex: Int, amount: Int) = repo.damageCharacter(enemyIndex, amount)

    fun onHealDamage(enemyIndex: Int, amount: Int) = repo.healCharacter(enemyIndex, amount)

    fun onRepairArmor(enemyIndex: Int, amount: Int) = repo.repairArmor(enemyIndex, amount)

    fun onModifyFear(enemyIndex: Int, amount: Int) = repo.modifyFear(enemyIndex, amount)

    /** The enemy the Manage Conditions screen will add to; set before navigating there. */
    private var conditionTargetIndex: Int? = null

    fun onManageConditions(enemyIndex: Int) {
        conditionTargetIndex = enemyIndex
    }

    fun onAddCondition(condition: Condition) {
        conditionTargetIndex?.let { repo.addCondition(it, condition) }
    }

    fun onModifyCondition(enemyIndex: Int, condition: Condition, amount: Int) =
        repo.modifyCondition(enemyIndex, condition, amount)

    fun onRenameEnemy(enemyIndex: Int, newName: String) {
        activeEncounter.characters.getOrNull(enemyIndex)?.let {
            it.name = newName
            publishState()
        }
    }

    fun onRenameEncounter(newName: String) {
        activeEncounter.name = newName
        publishState()
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
