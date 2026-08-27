package com.erhodes.falloutapp.server

import com.erhodes.falloutapp.data.DataManager
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.repository.CampaignRepository
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.EncounterRepository
import com.erhodes.falloutapp.repository.LocationRepository
import com.erhodes.falloutapp.repository.UserRepository
import com.erhodes.falloutapp.util.AppLogger
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

fun Application.falloutModule(
    userRepository: UserRepository,
    characterRepository: CharacterRepository,
    encounterRepository: EncounterRepository,
    locationRepository: LocationRepository,
    campaignRepository: CampaignRepository
    ) {
    install(ContentNegotiation) {
        json(
            Json {
                serializersModule = DataManager.serializerModule
            }
        )
    }

    /**
     * There needs to be 2 different connection endpoints. One is the user connecting. This just establishes that the connection works and in the future opens a websocket.
     * Two is the user registering a character for a campaign. This needs to add the character to the campaign and also save the character.
     *
     */

    routing {
        route("/users") {
            post {
                val string = call.receive<User>()
                AppLogger.d("Eric", "new user $string")
                userRepository.addUser(string)
                call.respond(HttpStatusCode.OK)
            }
        }
        route("/characters") {
            post {
                val characters = call.receive<List<PlayerCharacter>>()
                AppLogger.d("Eric", "received characters: ${characters.size}")

                characterRepository.addCharacters(characters)
                call.respond(characterRepository.characters)
            }
        }
        route("/encounters") {
            get {
                AppLogger.d("Eric", "GET active encounter")
                call.respond(encounterRepository.activeEncounter)
            }
            post {
                val character = call.receive<PlayerCharacter>()
                AppLogger.d("Eric", "received character: ${character.name}")
                encounterRepository.addCharacterToEncounter(character)
                call.respond(HttpStatusCode.OK)
            }
        }
        route("/campaign") {
            post {
                val character = call.receive<PlayerCharacter>()
                AppLogger.d("Eric", "received character: ${character.name} for ${character.ownerId}")
                userRepository.findUserById(character.ownerId)
                campaignRepository.addCharacterToCampaign(character)
                call.respond(HttpStatusCode.OK)
            }
            get {
                AppLogger.d("Eric", "got campaign data request")
                call.respond(campaignRepository.activeCampaign.summarize())
            }
            get("/location") {
                call.respond(locationRepository.activeLocation)
            }
        }
    }
}

fun startEmbeddedServer(
    userRepository: UserRepository,
    characterRepository: CharacterRepository,
    encounterRepository: EncounterRepository,
    locationRepository: LocationRepository,
    campaignRepository: CampaignRepository,
    port: Int = 8080
): EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration> {
    return embeddedServer(Netty, port = port, host = "0.0.0.0") {
        falloutModule(userRepository, characterRepository, encounterRepository, locationRepository, campaignRepository)
    }.start(wait = false)
}
