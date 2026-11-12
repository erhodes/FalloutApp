package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.LocalDataSource
import com.erhodes.falloutapp.data.NetworkDataSource
import com.erhodes.falloutapp.data.localIdStore
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.model.campaign.Campaign
import com.erhodes.falloutapp.network.StorePurchaseOutcome
import com.erhodes.falloutapp.network.StorePurchaseRequest
import com.erhodes.falloutapp.network.StorePurchaseResponse
import com.erhodes.falloutapp.util.AppLogger
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * The idea with this class is that it tracks the current status of the server connection.
 * It also handles requests to the server through the [NetworkDataSource].
 */
@OptIn(ExperimentalUuidApi::class)
class LoginRepository(
    private val characterRepository: CharacterRepository,
    private val encounterRepository: RemoteEncounterRepository,
    private val locationRepository: RemoteLocationRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    lateinit var dataSource: NetworkDataSource
    val localDataSource = LocalDataSource()

    var userId: String = ""
        private set

    // the issue with using the CompletableDeferred is it means getting the id is a suspend, which isn't great.
    // But without it theres the possibility of a race condition so I'm leaving it here as a reminder
    //    private val userIdReady = CompletableDeferred<String>()

    private val _loggedIn = MutableStateFlow(false)
    val loggedIn = _loggedIn.asStateFlow()

    private val _savedUsername = MutableStateFlow("")
    val savedUsername = _savedUsername.asStateFlow()

    private val _savedServerAddress = MutableStateFlow("")
    val savedServerAddress = _savedServerAddress.asStateFlow()

    private val _campaignFlow = MutableStateFlow(Campaign("Loading", ""))
    val campaignFlow = _campaignFlow.asStateFlow()

    private var serverAddress: String = ""

    init {
        AppLogger.d("Eric", "initing login repo")
        scope.launch {
            var uuid = localIdStore.get() ?: ""
            if (uuid.isEmpty()) {
                uuid = Uuid.random().toString()
                localIdStore.set(uuid)
            }
            userId = uuid
            AppLogger.d("Eric", "UUID: $userId")
//            userIdReady.complete(uuid)

            _savedUsername.value = localDataSource.getUsername()
            _savedServerAddress.value = localDataSource.getAddress()

            dataSource = NetworkDataSource(_savedServerAddress.value)
        }
    }

    /**
     * Intended to be called at app launch to establish a connection to a known server.
     */
    suspend fun automaticLogin() {
        if (!_loggedIn.value && _savedUsername.value.isNotEmpty() && _savedServerAddress.value.isNotEmpty()) {
            login (_savedUsername.value, _savedServerAddress.value)
        }
    }

    suspend fun manualLogin(username: String, address: String) {
        dataSource.newServerAddress(address)
        login(username, address)
    }

    private suspend fun login(username: String, address: String) {
//        val uuid = userIdReady.await()
        try {
            val success = dataSource.submitLoginRequest(User(userId, username))
            if (success) {
                serverAddress = address
                _savedUsername.value = username
                _savedServerAddress.value = address
                localDataSource.updateLoginInfo(username, address)
            }
            _loggedIn.value = success
        } catch (_: SocketTimeoutException) {
            _loggedIn.value = false
        }
    }

    suspend fun joinCampaign(character: PlayerCharacter) {
        dataSource.joinCampaign(character)
    }

    fun getCampaignData() {
        scope.launch {
            _campaignFlow.update {
                dataSource.getCampaignState()
            }
        }
    }

    suspend fun getActiveEncounter() {
        val remoteEncounter = dataSource.getActiveEncounter()
        encounterRepository.setActiveEncounter(remoteEncounter)
    }

    suspend fun getActiveLocation() {
        val remoteLocation = dataSource.getActiveLocation()
        locationRepository.setActiveLocation(remoteLocation)
    }

    /**
     * Buys one of a store's stock entries for [character].
     *
     * The server decides the sale, since it owns the stock. Only once it says the sale happened is
     * the spend replayed on the player's own character, and the location it sends back replaces the
     * cached one so the screen shows the stock it just decremented. A timeout or an unreachable
     * server leaves the character untouched, which is the right side to fail on: the server only
     * persists a sale it completed.
     */
    suspend fun purchaseFromStore(
        storeId: String,
        entryIndex: Int,
        character: PlayerCharacter
    ): StorePurchaseResponse {
        val response = try {
            dataSource.purchaseFromStore(StorePurchaseRequest(storeId, entryIndex, character))
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            // Covers a timeout, a dead connection, and never having logged in at all - in every
            // case nothing was bought, and the player should be told rather than shown a crash.
            StorePurchaseResponse(StorePurchaseOutcome.ERROR, "The store didn't answer.")
        }
        if (response.outcome == StorePurchaseOutcome.SUCCESS) {
            characterRepository.applyPurchase(character, response.templateId, response.price)
            response.location?.let { locationRepository.setActiveLocation(it) }
        }
        return response
    }
}
