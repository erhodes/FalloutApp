package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.data.store
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel() {
    private val repo: CharacterRepository = CharacterRepository()

    val characters = repo.characters

    private var activeCharacter = Character("empty")
    private val _activeCharacterState = MutableStateFlow(CharacterUiState(activeCharacter))
    val activeCharacterState = _activeCharacterState.asStateFlow()

    //TODO add DI and move this logic to the creation view model
    fun addCharacter(name: String, uiState: CharacterCreationUiState) {
        val newChar =
            Character(
                name = name,
                strength = uiState.strength,
                perception = uiState.perception,
                endurance = uiState.endurance,
                charisma = uiState.charisma,
                intelligence = uiState.intelligence,
                agility = uiState.agility,
                luck = uiState.luck
            )

        repo.add(newChar)
    }

    fun setActiveCharacter(character: Character) {
        activeCharacter = character
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
        }

    }

    fun addNewItemToActiveCharacter(newItem: ItemTemplate) {
        repo.addNewItemToCharacter(newItem, activeCharacter)
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
        }
    }

    fun removeItemFromActiveCharacter(item: Item) {
        repo.removeItemFromCharacter(item, activeCharacter)
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
        }
    }

    fun equipItemToCharacter(item: Item) {
        AppLogger.d("Eric","lets equip an item")
        repo.equipItemToCharacter(item, activeCharacter)
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
        }
    }

    fun unequipItemFromCharacter(item: Item) {
        AppLogger.d("Eric","lets UNequip an item")
        repo.unequipItemFromCharacter(item, activeCharacter)
        scope.launch {
            _activeCharacterState.update { CharacterUiState(activeCharacter) }
        }
    }
}