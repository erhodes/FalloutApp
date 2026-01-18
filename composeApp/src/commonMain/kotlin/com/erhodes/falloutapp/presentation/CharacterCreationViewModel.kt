package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class CharacterCreationViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel(), KoinComponent {

    private val repo: CharacterRepository by inject()
    private val loginRepository: LoginRepository by inject()

    var pointsRemaining = 7
    var majorsRemaining = 2
    var minorsRemaining = 3

    private var creationState = CharacterCreationUiState()
    private val _creationUiState = MutableStateFlow(CharacterCreationUiState())
    val creationUiState = _creationUiState.asStateFlow()

    fun addCharacter(name: String, uiState: CharacterCreationUiState): Character {
        val newChar =
            Character(
                name = name,
                ownerId = loginRepository.userId,
                strength = uiState.stats[0],
                perception = uiState.stats[1],
                endurance = uiState.stats[2],
                charisma = uiState.stats[3],
                intelligence = uiState.stats[4],
                agility = uiState.stats[5],
                luck = uiState.stats[6],
                skills = uiState.skills
            )

        repo.add(newChar)
        return newChar
    }

    fun startNewCreation() {
        pointsRemaining = 7
        majorsRemaining = 2
        minorsRemaining = 3

        updateState(CharacterCreationUiState())
    }

    fun incrementStat(position: Int) {
        if (creationState.stats[position] >= 3 || pointsRemaining <= 0) {
            return
        }
        pointsRemaining--
        creationState.stats[position]++
        updateState(
            creationState.copy(
                pointsRemaining = pointsRemaining,
            )
        )
    }

    fun decrementStat(position: Int) {
        if (creationState.stats[position] <= 1) {
            return
        }
        pointsRemaining++
        creationState.stats[position]--
        updateState(
            creationState.copy(
                pointsRemaining = pointsRemaining,
            )
        )
    }

    fun onMajorClicked(ordinal: Int) {
        if (creationState.skills[ordinal] == 5) {
            unselectMajorSkill(ordinal)
        } else {
            selectMajorSkill(ordinal)
        }
    }

    fun onMinorClicked(ordinal: Int) {
        if (creationState.skills[ordinal] == 4) {
            unselectMinorSkill(ordinal)
        } else {
            selectMinorSkill(ordinal)
        }
    }

    fun selectMajorSkill(ordinal: Int) {
        if (majorsRemaining <= 0) return
        majorsRemaining--
        // if this is 4, then it was a medium and needs unselecting
        if (creationState.skills[ordinal] == 4) {
            minorsRemaining++
        }
        creationState.skills[ordinal] = 5
        updateState(creationState.copy(
            majorsRemaining = majorsRemaining,
            minorsRemaining = minorsRemaining
        ))
    }

    fun unselectMajorSkill(ordinal: Int) {
        if (creationState.skills[ordinal] != 5) return
        majorsRemaining++
        creationState.skills[ordinal] = 2
        updateState(creationState.copy(
            majorsRemaining = majorsRemaining
        ))
    }

    fun selectMinorSkill(ordinal: Int) {
        if (minorsRemaining <= 0) return
        // if this skill is 5, it was a major and needs unselecting
        if (creationState.skills[ordinal] == 5) {
            majorsRemaining++
        }
        minorsRemaining--
        creationState.skills[ordinal] = 4
        updateState(creationState.copy(
            majorsRemaining = majorsRemaining,
            minorsRemaining = minorsRemaining
        ))
    }

    fun unselectMinorSkill(ordinal: Int) {
        if (creationState.skills[ordinal] != 4) return
        minorsRemaining++
        creationState.skills[ordinal] = 2
        updateState(creationState.copy(
            minorsRemaining = minorsRemaining
        ))
    }

    private fun updateState(newState: CharacterCreationUiState) {
        creationState = newState
        scope.launch {
            _creationUiState.update { newState }
        }
    }
}