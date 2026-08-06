package com.erhodes.falloutapp.model.campaign

import com.erhodes.falloutapp.model.PlayerCharacter

class Campaign(
    val name: String,
    val id: String
) {
    val activePlayers = ArrayList<PlayerCharacter>()

    fun addCharacter(character: PlayerCharacter) {
        activePlayers.add(character)
    }
}