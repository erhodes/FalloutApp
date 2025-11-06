package com.erhodes.falloutapp.aserialtest

import kotlinx.serialization.Serializable

@Serializable
class Car(override val speed: Int, override val template: VehicleTemplate) : Vehicle {
    val automatic = (template as CarTemplate).automatic
}