package com.erhodes.falloutapp.server

import com.erhodes.falloutapp.repository.CampaignRepository
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.EncounterRepository
import com.erhodes.falloutapp.repository.LocationRepository
import com.erhodes.falloutapp.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ServerManager: KoinComponent {
    val userRepository: UserRepository by inject()
    val characterRepository: CharacterRepository by inject()
    val encounterRepository: EncounterRepository by inject()
    val locationRepository: LocationRepository by inject()
    val campaignRepository: CampaignRepository by inject()

    fun startServer() {
        println("Server started")

        val engine = startEmbeddedServer(userRepository, characterRepository, encounterRepository, locationRepository = locationRepository, campaignRepository)

        Runtime.getRuntime().addShutdownHook(Thread { engine.stop(1000, 2000) })
    }


}