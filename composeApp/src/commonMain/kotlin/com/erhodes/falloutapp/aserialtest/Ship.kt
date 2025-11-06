package com.erhodes.falloutapp.aserialtest

import com.erhodes.falloutapp.model.ItemTemplate
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Ship(override val speed: Int, override val template: VehicleTemplate, @Transient val x: Int = 1) : Vehicle {
    var damageTaken: Int = 0
}