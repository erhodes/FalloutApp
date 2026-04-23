package com.erhodes.falloutapp.model.ability

class Radiation(val value: Int) : Ability(
    title = "Radiation $value",
    description = "Inflicts $value radiation on the target."
)
