package com.erhodes.falloutapp

import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.campaign.PurchaseResult
import com.erhodes.falloutapp.model.campaign.StockEntry
import com.erhodes.falloutapp.model.campaign.Store
import com.erhodes.falloutapp.presentation.StoreUiState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class StoreTests {

    @Test
    fun purchaseSucceeds() {
        val character = PlayerCharacter("Bob")
        giveCaps(character, 50)
        val store = Store("Mo's")
        val entry = StockEntry(rifleTemplate(), quantity = 2, price = 30)
        store.stock.add(entry)

        val result = store.sellTo(character, entry)

        val success = assertIs<PurchaseResult.Success>(result)
        assertEquals(ItemDataSource.ID_ASSAULT_RIFLE, success.item.template.id)
        assertTrue(success.item in character.inventory)
        assertEquals(20, capsCount(character))
        assertEquals(1, entry.quantity)
    }

    /** Caps the character can't cover must leave both sides of the transaction untouched. */
    @Test
    fun purchaseFailsWithoutEnoughCaps() {
        val character = PlayerCharacter("Bob")
        giveCaps(character, 10)
        val store = Store("Mo's")
        val entry = StockEntry(rifleTemplate(), quantity = 2, price = 30)
        store.stock.add(entry)
        val itemsBefore = character.inventory.size

        val result = store.sellTo(character, entry)

        val failure = assertIs<PurchaseResult.InsufficientCaps>(result)
        assertEquals(30, failure.required)
        assertEquals(10, failure.available)
        assertEquals(10, capsCount(character))
        assertEquals(itemsBefore, character.inventory.size)
        assertEquals(2, entry.quantity)
    }

    @Test
    fun purchaseFailsWhenOutOfStock() {
        val character = PlayerCharacter("Bob")
        giveCaps(character, 50)
        val store = Store("Mo's")
        val entry = StockEntry(rifleTemplate(), quantity = 0, price = 30)
        store.stock.add(entry)
        val itemsBefore = character.inventory.size

        assertIs<PurchaseResult.OutOfStock>(store.sellTo(character, entry))
        assertEquals(50, capsCount(character))
        assertEquals(itemsBefore, character.inventory.size)
        assertEquals(0, entry.quantity)
    }

    /** An entry this store doesn't hold must not decrement a quantity it doesn't own. */
    @Test
    fun purchaseFailsForEntryFromAnotherStore() {
        val character = PlayerCharacter("Bob")
        giveCaps(character, 50)
        val store = Store("Mo's")
        val foreignEntry = StockEntry(rifleTemplate(), quantity = 5, price = 30)

        assertIs<PurchaseResult.OutOfStock>(store.sellTo(character, foreignEntry))
        assertEquals(50, capsCount(character))
        assertEquals(5, foreignEntry.quantity)
    }

    /**
     * Caps max out at 50 per stack, so any real purse is spread across stacks. The price has to be
     * drawn from as many as it takes.
     */
    @Test
    fun purchaseDrainsCapsAcrossStacks() {
        val character = PlayerCharacter("Bob")
        giveCaps(character, 40)
        giveCaps(character, 40)
        assertEquals(80, capsCount(character))
        val store = Store("Mo's")
        val entry = StockEntry(rifleTemplate(), quantity = 1, price = 60)
        store.stock.add(entry)

        assertIs<PurchaseResult.Success>(store.sellTo(character, entry))
        assertEquals(20, capsCount(character))
    }

    @Test
    fun purchaseSpendingEveryCapSucceeds() {
        val character = PlayerCharacter("Bob")
        giveCaps(character, 30)
        val store = Store("Mo's")
        val entry = StockEntry(rifleTemplate(), quantity = 1, price = 30)
        store.stock.add(entry)

        assertIs<PurchaseResult.Success>(store.sellTo(character, entry))
        assertEquals(0, capsCount(character))
        assertEquals(0, entry.quantity)
    }

    /**
     * The store screen addresses an entry by its index, so the mapped list has to stay in the
     * model's order as well as carry the right numbers.
     */
    @Test
    fun storeUiStateCarriesStockInOrder() {
        val store = Store("Mo's", description = "Buys scrap.")
        store.stock.add(StockEntry(rifleTemplate(), quantity = 3, price = 120))
        store.stock.add(
            StockEntry(
                ItemDataSource.getItemTemplateById(ItemDataSource.ID_ARMOR_LEATHER),
                quantity = 0,
                price = 45
            )
        )

        val state = StoreUiState.from(store)

        assertEquals(store.id, state.id)
        assertEquals("Mo's", state.name)
        assertEquals("Buys scrap.", state.description)
        assertEquals(2, state.stock.size)

        val rifle = state.stock[0]
        assertEquals(ItemDataSource.ID_ASSAULT_RIFLE, rifle.templateId)
        assertEquals(rifleTemplate().name, rifle.name)
        assertEquals(3, rifle.quantity)
        assertEquals(120, rifle.price)

        val armor = state.stock[1]
        assertEquals(ItemDataSource.ID_ARMOR_LEATHER, armor.templateId)
        assertEquals(0, armor.quantity)
        assertEquals(45, armor.price)
    }

    private fun rifleTemplate() =
        ItemDataSource.getItemTemplateById(ItemDataSource.ID_ASSAULT_RIFLE)

    private fun giveCaps(character: PlayerCharacter, count: Int) {
        character.addItemToInventory(
            StackableItem(ItemDataSource.getItemTemplateById(ItemDataSource.ID_CAPS), count)
        )
    }

    private fun capsCount(character: PlayerCharacter): Int =
        character.countStackable(ItemDataSource.ID_CAPS)
}
