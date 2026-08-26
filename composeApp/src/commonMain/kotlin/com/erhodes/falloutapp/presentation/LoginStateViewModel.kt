package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.LoginRepository
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginStateViewModel : ViewModel(), KoinComponent {
    private val repo: LoginRepository by inject()
    private val characterRepo: CharacterRepository by inject()

    val loginState = repo.loggedIn
    val savedUsername = repo.savedUsername
    val savedServerAddress = repo.savedServerAddress

    val campaignFlow = repo.campaignFlow

    fun automaticLogin() {
        viewModelScope.launch {
            repo.automaticLogin()
        }
    }

    fun login(name: String, address: String) {
        viewModelScope.launch {
            repo.login(name, address)
        }
    }

    fun sync() {
        viewModelScope.launch {
            val characters = characterRepo.characters
            repo.syncCharacters(characters)
        }
    }

    fun joinCampaign(character: PlayerCharacter) {
        viewModelScope.launch {
            repo.joinCampaign(character)
        }
    }

    fun syncCampaign() {
        repo.getCampaignData()
    }

    /** [onLoaded] runs on the main thread once the encounter is in the repository. */
    fun getActiveEncounter(onLoaded: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                repo.getActiveEncounter()
                onLoaded()
            } catch (e: Exception) {
                AppLogger.d("Eric", "failed to get active encounter: ${e.message}")
            }
        }
    }

    fun getActiveLocation() {
        viewModelScope.launch {
            try {
                repo.getActiveLocation()
            } catch (e: Exception) {
                AppLogger.d("Eric", "failed to get active location: ${e.message}")
            }
        }
    }
}
