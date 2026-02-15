package com.erhodes.falloutapp.model.ability

class RadResistant(val value: Int) : Ability(
    title = "Rad Resistant $value",
    description = "Lowers all radiation damage by $value."
)