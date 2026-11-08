package com.erhodes.falloutapp.model.campaign

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
sealed class Location {
    abstract val id: String
    abstract var name: String
    abstract var description: String

    /**
     * The [Feature]s this location contains. A `val` holding a mutable list: features are added to
     * and removed from the list in place, the property itself is never reassigned.
     */
    abstract val features: MutableList<Feature>
}

/** A location with no special campaign mechanics attached to it. */
@OptIn(ExperimentalUuidApi::class)
@Serializable
@SerialName("basic_location")
class BasicLocation(
    override var name: String,
    override var description: String = "",
    override val id: String = Uuid.random().toString(),
    override val features: MutableList<Feature> = mutableListOf()
) : Location()
