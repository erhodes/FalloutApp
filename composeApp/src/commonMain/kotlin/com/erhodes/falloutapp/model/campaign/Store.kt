package com.erhodes.falloutapp.model.campaign

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
    override val id: String = Uuid.random().toString()
) : Feature()
