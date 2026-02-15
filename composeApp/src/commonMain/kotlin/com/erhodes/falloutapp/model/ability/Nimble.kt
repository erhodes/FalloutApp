package com.erhodes.falloutapp.model.ability

class Nimble(val value: Int) : Ability(
    title = "Nimble $value",
    description = "Before or after making an attack with this weapon, move $value. This movement cannot trigger reactions."
)