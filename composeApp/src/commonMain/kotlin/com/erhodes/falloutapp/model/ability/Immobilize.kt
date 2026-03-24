package com.erhodes.falloutapp.model.ability

class Immobilize(val value: Int) : Ability(
    title = "Immobilize $value",
    description = "Inflicts the Immobilized $value condition on the target, lowering their speed to 0."
)