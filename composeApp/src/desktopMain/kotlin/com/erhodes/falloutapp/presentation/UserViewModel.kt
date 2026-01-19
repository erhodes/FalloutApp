package com.erhodes.falloutapp.presentation

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class UserCharacterGroup(
    val userName: String,
    val characters: List<Character>
)

class UserViewModel: ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val characterRepository: CharacterRepository by inject()

    val users = userRepository.users

    val userCharacterGroups: StateFlow<List<UserCharacterGroup>> =
        userRepository.users.combine(snapshotFlow { characterRepository.characters.toList() }) { users, characters ->
            characters.groupBy { it.ownerId }
                .mapNotNull { (ownerId, charList) ->
                    users.find { it.uuid == ownerId }?.let { user ->
                        UserCharacterGroup(user.name, charList)
                    }
                }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
