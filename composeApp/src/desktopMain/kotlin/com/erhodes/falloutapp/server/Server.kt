package com.erhodes.falloutapp.server

import com.erhodes.falloutapp.data.DataManager
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.model.campaign.PurchaseResult
import com.erhodes.falloutapp.model.campaign.Store
import com.erhodes.falloutapp.network.StorePurchaseOutcome
import com.erhodes.falloutapp.network.StorePurchaseRequest
import com.erhodes.falloutapp.network.StorePurchaseResponse
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
            /**
             * Sells a player one of the active location's store stock. The server owns the stock, so
             * it is the one that decides: it adopts the character the player sent (they own that),
             * runs the sale, and persists the location only if the sale actually happened.
             */
            post("/store/purchase") {
                val request = call.receive<StorePurchaseRequest>()
                AppLogger.d(
                    "Eric",
                    "purchase: ${request.character.name} wants entry ${request.entryIndex} " +
                        "from store ${request.storeId}"
                )
                // Adopt the player's copy first. Ours may be stale from the last sync, and spending
                // from a stale copy could hand out caps the player has already spent elsewhere.
                characterRepository.addCharacters(listOf(request.character))
                val character = request.character

                val location = locationRepository.activeLocation
                val store = location.features
                    .filterIsInstance<Store>()
                    .firstOrNull { it.id == request.storeId }
                if (store == null) {
                    call.respond(
                        StorePurchaseResponse(
                            outcome = StorePurchaseOutcome.STORE_NOT_FOUND,
                            message = "That store isn't here any more."
                        )
                    )
                    return@post
                }

                val entry = store.stock.getOrNull(request.entryIndex)
                if (entry == null) {
                    call.respond(
                        StorePurchaseResponse(
                            outcome = StorePurchaseOutcome.OUT_OF_STOCK,
                            message = "That item isn't stocked any more."
                        )
                    )
                    return@post
                }

                // Read both before the sale: it decrements the entry we're about to report on.
                val itemName = entry.template.name
                val templateId = entry.template.id
                val price = entry.price

                val response = when (
                    val result = characterRepository.purchaseFromStore(store, entry, character)
                ) {
                    is PurchaseResult.Success -> {
                        locationRepository.saveLocation(location)
                        StorePurchaseResponse(
                            outcome = StorePurchaseOutcome.SUCCESS,
                            message = "Bought $itemName for $price caps.",
                            templateId = templateId,
                            price = price,
                            location = location
                        )
                    }
                    is PurchaseResult.InsufficientCaps -> StorePurchaseResponse(
                        outcome = StorePurchaseOutcome.INSUFFICIENT_CAPS,
                        message = "$itemName costs ${result.required} caps, " +
                            "and you have ${result.available}."
                    )
                    PurchaseResult.OutOfStock -> StorePurchaseResponse(
                        outcome = StorePurchaseOutcome.OUT_OF_STOCK,
                        message = "$itemName is out of stock."
                    )
                }
                call.respond(response)
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
