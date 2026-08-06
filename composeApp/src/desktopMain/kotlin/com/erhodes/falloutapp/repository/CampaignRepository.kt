package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.campaign.Campaign
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.PlayerCharacter
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CampaignRepository {
    var activeCampaign = Campaign("Test Campaign", "1")

    private val _changes = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val changes: SharedFlow<Unit> = _changes.asSharedFlow()

    init {
        activeCampaign.addCharacter(PlayerCharacter(name="Bob McTest"))
    }

    fun addCharacterToCampaign(character: PlayerCharacter) {
        activeCampaign.addCharacter(character)
        notifyChanged()
    }

    private fun notifyChanged() {
        _changes.tryEmit(Unit)
    }
}