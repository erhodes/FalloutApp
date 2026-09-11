package com.erhodes.falloutapp

import com.erhodes.falloutapp.data.DataManager
import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.model.condition.ConditionTemplate
import com.erhodes.falloutapp.model.condition.ScalableCondition
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement
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
}