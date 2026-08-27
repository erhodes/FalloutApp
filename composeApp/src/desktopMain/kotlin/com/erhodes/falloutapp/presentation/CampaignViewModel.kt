package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.campaign.Campaign
import com.erhodes.falloutapp.repository.CampaignRepository
import com.erhodes.falloutapp.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class CampaignViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel(), KoinComponent {
    private val repo: CampaignRepository by inject()
    private val userRepository: UserRepository by inject()
    var activeCampaign: Campaign = repo.activeCampaign

    private val _activeCampaignState = MutableStateFlow(buildState())
    val activeCampaignState = _activeCampaignState.asStateFlow()

    init {
        publishState()
        scope.launch {
            repo.changes.collect { publishState() }
        }
        scope.launch {
            userRepository.users.collect { publishState() }
        }
    }

    private fun buildState() = CampaignUiState(
        campaign = activeCampaign,
        players = activeCampaign.playerCharacters.map { entry ->
            CampaignPlayerRow(
                ownerName = userRepository.findUserById(entry.value.ownerId)?.name ?: "Unknown",
                characterName = entry.value.name,
            )
        }
    )

    private fun publishState() {
        scope.launch {
            _activeCampaignState.update { buildState() }
        }
    }
}