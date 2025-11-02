package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.util.AppLogger
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

    val stats = arrayListOf<Int>(1,1,1,1,1,1,1)

    private val _creationUiState = MutableStateFlow(CharacterCreationUiState())
    val creationUiState = _creationUiState.asStateFlow()

    fun startNewCreation() {
        pointsRemaining = 7
        stats.map { 1 }
    }

    fun onComplete() {

    }

    fun incrementStat(position: Int) {
        if (stats[position] >= 3 || pointsRemaining <= 0) {
            return
        }
        pointsRemaining--
        stats[position]++
        AppLogger.d("Eric","stat $position is now ${stats[position]}")
        scope.launch {
            _creationUiState.update { CharacterCreationUiState(
                pointsRemaining,
                stats[0],
                stats[1],
                stats[2],
                stats[3],
                stats[4],
                stats[5],
                stats[6],
                )
            }
        }
    }

    fun decrementStat(position: Int) {
        if (stats[position] <= 1) {
            return
        }
        pointsRemaining++
        stats[position]--
        scope.launch {
            _creationUiState.update { CharacterCreationUiState(
                pointsRemaining,
                stats[0],
                stats[1],
                stats[2],
                stats[3],
                stats[4],
                stats[5],
                stats[6],
                )
            }
        }
    }
}