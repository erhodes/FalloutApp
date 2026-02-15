package com.erhodes.falloutapp.model.ability

class Ignite(val value: Int) : Ability(
    title = "Ignite $value",
    description = "Inflicts $value Burn on a target. At the end of their turn, their Burn value will decrease by 1 and they will suffer 1 damage (bypassing Toughness)."
)