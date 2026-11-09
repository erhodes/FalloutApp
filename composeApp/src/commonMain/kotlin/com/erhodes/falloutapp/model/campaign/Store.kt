package com.erhodes.falloutapp.model.campaign

import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.createItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
@SerialName("store")
class Store(
    override var name: String,
    override var description: String = "",
    override val id: String = Uuid.random().toString(),
    /**
     * What this store sells. A `val` holding a mutable list: entries are added, removed and
     * restocked in place, the property itself is never reassigned.
     */
    val stock: MutableList<StockEntry> = mutableListOf(),
) : Feature() {

    /**
     * Sells one of [entry] to [character]: caps out of their inventory, the item into it, and the
     * entry's quantity down by one.
     *
     * Named for the direction of the transaction so `store.sellTo(buyer, entry)` can't be misread.
     * Both failures return before anything is mutated, so a refused sale leaves the character and
     * the store exactly as they were. An entry that runs out stays in [stock] at zero, so the store
     * still lists what it carries and raising the number restocks it.
     */
    fun sellTo(character: PlayerCharacter, entry: StockEntry): PurchaseResult {
        // Guard membership too: an entry from another store would decrement stock we don't own.
        if (entry !in stock || entry.quantity <= 0) return PurchaseResult.OutOfStock

        val available = character.countStackable(ItemDataSource.ID_CAPS)
        if (available < entry.price) {
            return PurchaseResult.InsufficientCaps(required = entry.price, available = available)
        }

        character.consumeStackable(ItemDataSource.ID_CAPS, entry.price)
        val item = entry.template.createItem()
        character.addItemToInventory(item)
        entry.quantity--
        return PurchaseResult.Success(item)
    }
}
