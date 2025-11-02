package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterCreationViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel() {
    var pointsRemaining = 7

    var majorsRemaining = 2
    var minorsRemaining = 3

    val stats = arrayListOf<Int>(1,1,1,1,1,1,1)

    private var creationState = CharacterCreationUiState()
    private val _creationUiState = MutableStateFlow(CharacterCreationUiState())
    val creationUiState = _creationUiState.asStateFlow()

    fun startNewCreation() {
        pointsRemaining = 7
        stats.map { 1 }

        creationState = CharacterCreationUiState()
    }

    fun onComplete() {

    }

    fun incrementStat(position: Int) {
        if (stats[position] >= 3 || pointsRemaining <= 0) {
            return
        }
        pointsRemaining--
        stats[position]++
        updateState(
            creationState.copy(
                pointsRemaining = pointsRemaining,
                strength = stats[0],
                perception = stats[1],
                endurance = stats[2],
                charisma = stats[3],
                intelligence = stats[4],
                agility = stats[5],
                luck = stats[6],
            )
        )
    }

    fun decrementStat(position: Int) {
        if (stats[position] <= 1) {
            return
        }
        pointsRemaining++
        stats[position]--
        updateState(
            creationState.copy(
                pointsRemaining = pointsRemaining,
                strength = stats[0],
                perception = stats[1],
                endurance = stats[2],
                charisma = stats[3],
                intelligence = stats[4],
                agility = stats[5],
                luck = stats[6],
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
//        val newState = CharacterCreationUiState(
//            creationState.pointsRemaining,
//            stats[0],
//            stats[1],
//            stats[2],
//            stats[3],
//            stats[4],
//            stats[5],
//            stats[6],
//            creationState.skills
//        )
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