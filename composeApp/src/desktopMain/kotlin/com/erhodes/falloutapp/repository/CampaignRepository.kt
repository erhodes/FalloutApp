package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.CampaignDataSource
import com.erhodes.falloutapp.model.campaign.Campaign
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.PlayerCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CampaignRepository(
    private val dataSource: CampaignDataSource,
    private val locationRepository: LocationRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    var activeCampaign = Campaign("Test Campaign", "1")

    private val _changes = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val changes: SharedFlow<Unit> = _changes.asSharedFlow()

    init {
        // The campaign is loaded asynchronously; observers pick it up via the changes flow.
        scope.launch {
            // A location that has since been deleted leaves the placeholder in place.
            dataSource.loadCampaignRecord(activeCampaign.id)?.currentLocationId
                ?.let { locationRepository.loadLocation(it) }
                ?.let { activeCampaign.currentLocation = it }
            dataSource.loadCharacters(activeCampaign.id).forEach { activeCampaign.addCharacter(it) }
            notifyChanged()
        }
    }

    fun addCharacterToCampaign(character: PlayerCharacter) {
        activeCampaign.addCharacter(character)
        scope.launch { dataSource.saveCharacter(activeCampaign.id, character) }
        notifyChanged()
    }

    /** Moves the players of the active campaign to [location]. */
    fun setCurrentLocation(location: Location) {
        activeCampaign.currentLocation = location
        scope.launch { dataSource.saveCampaign(activeCampaign) }
        notifyChanged()
    }

    private fun notifyChanged() {
        _changes.tryEmit(Unit)
    }
}
