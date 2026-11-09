package com.erhodes.falloutapp.network

import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.campaign.Location
import kotlinx.serialization.Serializable

/**
 * A player's request to buy one of a store's stock entries.
 *
 * The buyer is sent whole rather than by id because [PlayerCharacter] has no id - only `ownerId`,
 * which is empty for characters created before a login - and because the player's own copy is the
 * authoritative one. The server adopts it before spending from it, so a mirror it has been holding
 * since the last sync can't spend caps the player has already used.
 *
 * [entryIndex] addresses the entry by its position in the store's stock, the same convention the
 * store screen uses. A stale index can only resolve to a different entry or to nothing, and the
 * server re-checks stock and price either way before anything is spent.
 */
@Serializable
class StorePurchaseRequest(
    val storeId: String,
    val entryIndex: Int,
    val character: PlayerCharacter,
)

/**
 * What the server did about a [StorePurchaseRequest].
 *
 * [com.erhodes.falloutapp.model.campaign.PurchaseResult] can't cross the wire - it carries a live
 * [com.erhodes.falloutapp.model.Item] and is deliberately not serializable - so the outcome is
 * flattened here. On success the client has everything it needs to apply the same purchase to its
 * own copy of the character: [templateId] says what was bought and [price] what it cost.
 *
 * [location] is the location as the server now holds it, so the player's screen shows the decremented
 * stock without a second round trip.
 */
@Serializable
class StorePurchaseResponse(
    val outcome: StorePurchaseOutcome,
    val message: String,
    val templateId: Int = 0,
    val price: Int = 0,
    val location: Location? = null,
)

@Serializable
enum class StorePurchaseOutcome {
    SUCCESS,
    OUT_OF_STOCK,
    INSUFFICIENT_CAPS,

    /** The GM has moved on, or deleted the store, since the player last loaded the location. */
    STORE_NOT_FOUND,

    /** The request never got an answer - no connection, a timeout, or a server error. */
    ERROR,
}
