package com.erhodes.falloutapp.model.campaign

import com.erhodes.falloutapp.model.PlayerCharacter

class Campaign(
    val name: String,
    val id: String
) {
    val playerCharacters = HashMap<String, PlayerCharacter>()

    /**
     * Where the players currently are. Distinct from the location the GM happens to be viewing
     * (LocationRepository.activeLocation); this is the one players are served.
     */
    var currentLocation: Location = BasicLocation("Test Location")

    fun addCharacter(character: PlayerCharacter) {
        playerCharacters[character.ownerId] = character
    }

    fun summarize(): CampaignSummary {
        return CampaignSummary(name, id)
    }
}