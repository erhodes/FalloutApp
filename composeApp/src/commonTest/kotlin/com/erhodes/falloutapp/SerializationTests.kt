package com.erhodes.falloutapp

import com.erhodes.falloutapp.data.DataManager
import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.model.condition.ConditionTemplate
import com.erhodes.falloutapp.model.condition.ScalableCondition
import kotlinx.serialization.json.Json
import kotlin.test.Test

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
}