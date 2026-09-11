package com.erhodes.falloutapp.model.campaign

import kotlinx.serialization.Serializable

@Serializable
open class Location(val id: Int, var name: String, var description: String = "") {
}
