package com.erhodes.falloutapp.model.ability

class Suppress(val value: Int) : Ability(
    title = "Suppress $value",
    description = "A target with Int <= $value is Suppressed(they must spend an action on their turn either Hunkering Down or moving towards cover)."
)