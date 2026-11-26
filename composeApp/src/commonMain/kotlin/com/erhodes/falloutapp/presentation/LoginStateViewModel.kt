package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.LoginRepository
import com.erhodes.falloutapp.util.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    /** True once Join Campaign has succeeded; persisted by [LoginRepository] across restarts. */
    val campaignJoined: StateFlow<Boolean> = repo.joinedCampaignId
        .map { it.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, repo.joinedCampaignId.value.isNotEmpty())

    /**
     * Whether the Connect section of the Login screen is expanded. Held here (not in the screen)
     * so it survives leaving and returning to the Login destination.
     */
    private val _connectExpanded = MutableStateFlow(!repo.loggedIn.value)
    val connectExpanded = _connectExpanded.asStateFlow()

    init {
        // Collapse automatically only when a connection is newly established, so a manual
        // expand while already connected is preserved.
        viewModelScope.launch {
            var wasLoggedIn = repo.loggedIn.value
            repo.loggedIn.collect { loggedIn ->
                if (loggedIn && !wasLoggedIn) {
                    _connectExpanded.value = false
                }
                wasLoggedIn = loggedIn
            }
        }
    }

    fun setConnectExpanded(expanded: Boolean) {
        _connectExpanded.value = expanded
    }

    fun automaticLogin() {
        viewModelScope.launch {
            repo.automaticLogin()
        }
    }

    fun login(name: String, address: String) {
        viewModelScope.launch {
            repo.manualLogin(name, address)
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
