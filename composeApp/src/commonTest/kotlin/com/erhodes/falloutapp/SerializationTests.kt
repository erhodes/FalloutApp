package com.erhodes.falloutapp

import com.erhodes.falloutapp.data.DataManager
import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.model.condition.ConditionTemplate
import com.erhodes.falloutapp.model.condition.ScalableCondition
import com.erhodes.falloutapp.model.campaign.BasicFeature
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement
import com.erhodes.falloutapp.model.campaign.StockEntry
import com.erhodes.falloutapp.model.campaign.Store
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SerializationTests {
    val json: Json = Json {
        serializersModule = DataManager.serializerModule
        ignoreUnknownKeys = true
        classDiscriminator = "type"
    }

    @Test
    fun serializeCondition() {
        val cond = Condition.buildNewCondition(ConditionTemplate.Burning)
        val jsonString = json.encodeToString<Condition>(cond)
        println(jsonString)

        val deserialized = json.decodeFromString<Condition>(jsonString) as ScalableCondition
        println("${deserialized.count}")
    }

    @Test
    fun serializeEncounter() {
        val encounter = Encounter("Battle")
        val psycho = EnemyDataSource.createRaiderPsycho()
        val bob = PlayerCharacter("Bob")
        encounter.addCharacter(psycho)
        encounter.addCharacter(bob)

        val jsonString = json.encodeToString<Encounter>(encounter)
        println(jsonString)

        val deserialized = json.decodeFromString<Encounter>(jsonString)
        println(deserialized.name)
    }

    /**
     * Locations are stored as a JSON blob encoded against the sealed base, so a Settlement has to
     * come back as a Settlement with its own fields intact.
     */
    @Test
    fun serializeSettlement() {
        val settlement = Settlement(
            name = "Honeywell",
            description = "A walled farming town.",
            food = 4,
            population = 12
        )

        val jsonString = json.encodeToString<Location>(settlement)
        println(jsonString)

        val deserialized = json.decodeFromString<Location>(jsonString)
        assertIs<Settlement>(deserialized)
        assertEquals(settlement.id, deserialized.id)
        assertEquals("Honeywell", deserialized.name)
        assertEquals("A walled farming town.", deserialized.description)
        assertEquals(4, deserialized.food)
        assertEquals(12, deserialized.population)
    }

    /**
     * Features live inside the location blob, so each one has to carry its own "type" discriminator
     * and come back as its own subclass.
     */
    @Test
    fun serializeLocationFeatures() {
        val well = BasicFeature(name = "Water Well", description = "Hand-cranked, brackish.")
        val trader = Store(name = "Mo's Trading Post", description = "Buys scrap, sells ammo.")
        val settlement = Settlement(
            name = "Honeywell",
            description = "A walled farming town.",
            features = mutableListOf(well, trader)
        )

        val jsonString = json.encodeToString<Location>(settlement)
        println(jsonString)

        val deserialized = json.decodeFromString<Location>(jsonString)
        assertIs<Settlement>(deserialized)
        assertEquals(2, deserialized.features.size)

        val deserializedWell = deserialized.features[0]
        assertIs<BasicFeature>(deserializedWell)
        assertEquals(well.id, deserializedWell.id)
        assertEquals("Water Well", deserializedWell.name)
        assertEquals("Hand-cranked, brackish.", deserializedWell.description)

        val deserializedTrader = deserialized.features[1]
        assertIs<Store>(deserializedTrader)
        assertEquals(trader.id, deserializedTrader.id)
        assertEquals("Mo's Trading Post", deserializedTrader.name)
    }

    /** Rows written before features existed have no "features" key; they must still decode. */
    @Test
    fun deserializeLocationWithoutFeatures() {
        val legacy = """{"type":"settlement","name":"Stillwater","description":"","food":2,"population":3,"id":"legacy-stillwater"}"""

        val deserialized = json.decodeFromString<Location>(legacy)
        assertIs<Settlement>(deserialized)
        assertEquals("Stillwater", deserialized.name)
        assertEquals(0, deserialized.features.size)
    }

    /**
     * A Store's stock is persisted inside the location blob, and an ItemTemplate serializes as its
     * catalog id, so the stock has to come back pointing at the same templates with its numbers
     * intact.
     */
    @Test
    fun serializeStoreStock() {
        val store = Store(name = "Mo's Trading Post")
        store.stock.add(
            StockEntry(
                template = ItemDataSource.getItemTemplateById(ItemDataSource.ID_ASSAULT_RIFLE),
                quantity = 3,
                price = 120
            )
        )
        store.stock.add(
            StockEntry(
                template = ItemDataSource.getItemTemplateById(ItemDataSource.ID_CAPS),
                quantity = 0,
                price = 1
            )
        )
        val settlement = Settlement(name = "Honeywell", features = mutableListOf(store))

        val jsonString = json.encodeToString<Location>(settlement)
        println(jsonString)

        val deserialized = json.decodeFromString<Location>(jsonString)
        assertIs<Settlement>(deserialized)
        val deserializedStore = deserialized.features.single()
        assertIs<Store>(deserializedStore)
        assertEquals(2, deserializedStore.stock.size)

        val rifle = deserializedStore.stock[0]
        assertEquals(ItemDataSource.ID_ASSAULT_RIFLE, rifle.template.id)
        assertEquals(3, rifle.quantity)
        assertEquals(120, rifle.price)

        // An entry sold out to zero is kept, so the store still lists what it carries.
        val caps = deserializedStore.stock[1]
        assertEquals(ItemDataSource.ID_CAPS, caps.template.id)
        assertEquals(0, caps.quantity)
        assertEquals(1, caps.price)
    }
}
