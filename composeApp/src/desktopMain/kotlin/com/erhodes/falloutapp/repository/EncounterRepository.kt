package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.EncounterDataSource
import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Encounter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EncounterRepository(
    private val dataSource: EncounterDataSource
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    var activeEncounter = Encounter("Test Encounter")
        private set

    private val _changes = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val changes: SharedFlow<Unit> = _changes.asSharedFlow()

    init {
        // todo temporary test data
        activeEncounter.addCharacter(EnemyDataSource.createRaiderShotgunner())
        activeEncounter.addCharacter(EnemyDataSource.createRaiderPsycho())
        activeEncounter.addCharacter(EnemyDataSource.createRaiderLieutenant())
        notifyChanged()
    }

    fun addCharacterToEncounter(character: Character) {
        activeEncounter.addCharacter(character)
        notifyChanged()
    }

    fun damageCharacter(index: Int, amount: Int) {
        activeEncounter.characters.getOrNull(index)?.let {
            it.takeDamage(amount)
            notifyChanged()
        }
    }

    fun healCharacter(index: Int, amount: Int) {
        activeEncounter.characters.getOrNull(index)?.let {
            it.healDamage(amount)
            notifyChanged()
        }
    }

    fun repairArmor(index: Int, amount: Int) {
        activeEncounter.characters.getOrNull(index)?.let {
            it.repairArmor(amount)
            notifyChanged()
        }
    }

    fun removeCharacter(index: Int) {
        activeEncounter.removeCharacter(index)
        notifyChanged()
    }

    /** Persist the active encounter to the database (PlayerCharacters are filtered out by the data source). */
    fun saveActiveEncounter() {
        scope.launch { dataSource.save(activeEncounter) }
    }

    suspend fun loadEncounter(id: String): Encounter? = dataSource.load(id)

    suspend fun loadAllEncounters(): List<Encounter> = dataSource.loadAll()

    fun deleteEncounter(id: String) {
        scope.launch { dataSource.delete(id) }
    }

    /** Swap in a loaded encounter as the active one and notify observers. */
    fun setActiveEncounter(encounter: Encounter) {
        activeEncounter = encounter
        notifyChanged()
    }

    private fun notifyChanged() {
        _changes.tryEmit(Unit)
    }
}
