package com.erhodes.falloutapp.model.campaign

import com.erhodes.falloutapp.model.PlayerCharacter

class Campaign(
    val name: String,
    val id: String
) {
    val playerCharacters = HashMap<String, PlayerCharacter>()

    fun addCharacter(character: PlayerCharacter) {
        playerCharacters[character.ownerId] = character
    }

    fun summarize(): CampaignSummary {
        return CampaignSummary(name, id)
    }
}