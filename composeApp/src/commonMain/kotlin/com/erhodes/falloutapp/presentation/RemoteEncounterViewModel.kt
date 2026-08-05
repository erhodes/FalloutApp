package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.repository.RemoteEncounterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Exposes the encounter fetched from the server for display. The client can't modify it, so there
 * are no mutation entry points here.
 */
class RemoteEncounterViewModel : ViewModel(), KoinComponent {
    private val repo: RemoteEncounterRepository by inject()

    val activeEncounterState = repo.activeEncounter
        .map { it?.toUiState() ?: EncounterUiState.EMPTY }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EncounterUiState.EMPTY)

    private fun Encounter.toUiState() = EncounterUiState(
        name = name,
        enemies = characters.mapIndexed { i, c -> EnemyUiState.from(i, c) }
    )
}
