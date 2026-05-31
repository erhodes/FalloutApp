package com.erhodes.falloutapp.model

class Campaign(
    val name: String,
    val id: String
) {
    val activePlayers = ArrayList<PlayerCharacter>()

    fun addCharacter(character: PlayerCharacter) {
        activePlayers.add(character)
    }
}