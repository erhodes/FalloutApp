package com.erhodes.falloutapp.model.campaign

import com.erhodes.falloutapp.model.Item

/**
 * The outcome of [Store.sellTo]. Each failure names its own reason so a caller can tell the player
 * why nothing happened rather than just that it didn't. Not serializable - a result is a transient
 * answer about one attempt, never part of a saved store.
 */
sealed interface PurchaseResult {

    /** The sale went through; [item] is the instance now in the buyer's inventory. */
    data class Success(val item: Item) : PurchaseResult

    /** The store has none left, or the entry isn't part of this store's stock at all. */
    data object OutOfStock : PurchaseResult

    /** The buyer couldn't cover [required] caps, holding only [available]. Nothing was spent. */
    data class InsufficientCaps(val required: Int, val available: Int) : PurchaseResult
}
