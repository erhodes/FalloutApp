package com.erhodes.falloutapp.model.campaign

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Something a [Location] contains. Sealed like [Location] itself so subtypes carry a "type"
 * discriminator and survive the JSON round trip the location blob goes through; later subclasses can
 * add their own fields without any serializers-module wiring.
 */
@Serializable
sealed class Feature {
    abstract val id: String
    abstract var name: String
    abstract var description: String
}

/** A feature with no special campaign mechanics attached to it. */
@OptIn(ExperimentalUuidApi::class)
@Serializable
@SerialName("basic_feature")
class BasicFeature(
    override var name: String,
    override var description: String = "",
    override val id: String = Uuid.random().toString()
) : Feature()
