package com.erhodes.falloutapp.model.ability

class Frighten(val value: Int) : Ability(
    title = "Suppress $value",
    description = "Inflicts $value Fear on the target."
)