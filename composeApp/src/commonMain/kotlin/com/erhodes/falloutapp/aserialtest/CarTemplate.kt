package com.erhodes.falloutapp.aserialtest

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

class CarTemplate(id: Int, val automatic: Boolean) : VehicleTemplate(id) {
}