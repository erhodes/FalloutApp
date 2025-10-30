package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
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

//    private val _activeCharacterFlow = MutableSharedFlow<Character>(1)
//    val activeCharacterFlow = _activeCharacterFlow.asSharedFlow()

    fun addCharacter(name: String) {
        repo.add(name)
    }

    fun setActiveCharacter(character: Character) {
        activeCharacter = character
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
//            _activeCharacterFlow.emit(activeCharacter)
//            _activeCharacterState.emit(activeCharacter)
        }

    }

    fun addNewItemToActiveCharacter(newItem: ItemTemplate) {
        activeCharacter.addItemToInventory(Item(newItem))
    }

    fun equipItemToCharacter(item: Item) {
        AppLogger.d("Eric","lets equip an item")
        activeCharacter.equipItem(item)
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
//            _activeCharacterFlow.emit(activeCharacter)
//            _activeCharacterState.update{ activeCharacter }
        }
    }

    fun unequipItemFromCharacter(item: Item) {
        AppLogger.d("Eric","lets UNequip an item")
        activeCharacter.unequipItem(item)
        scope.launch {
            _activeCharacterState.update { CharacterUiState(activeCharacter) }
        }
    }
}