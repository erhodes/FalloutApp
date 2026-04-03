package com.erhodes.falloutapp

import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.model.condition.ConditionTemplate
import com.erhodes.falloutapp.model.condition.ScalableCondition
import kotlinx.serialization.json.Json
import kotlin.test.Test

class SerializationTests {

    @Test
    fun serializeCondition() {
        val cond = Condition.buildNewCondition(ConditionTemplate.Burning)
        val jsonString = Json.encodeToString<Condition>(cond)
        println(jsonString)

        val deserialized = Json.decodeFromString<Condition>(jsonString) as ScalableCondition
        println("${deserialized.count}")
    }

}