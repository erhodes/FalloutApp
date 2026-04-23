package com.erhodes.falloutapp.model.ability

class Maim(val value: Int) : Ability(
    title = "Maim $value",
    description = "Inflicts $value additional damage to the target, so long as they had no armor durability remaining at the start of the attack."
)
