package com.erhodes.falloutapp.model.campaign

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
@SerialName("settlement")
class Settlement(
    override var name: String,
    override var description: String = "",
    var food: Int = 1,
    var population: Int = 1,
    override val id: String = Uuid.random().toString(),
    override val features: MutableList<Feature> = mutableListOf()
) : Location()
