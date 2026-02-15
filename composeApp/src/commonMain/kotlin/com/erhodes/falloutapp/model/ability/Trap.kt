package com.erhodes.falloutapp.model.ability

class Trap(val value: Int) : Ability(
    title = "Trap $value",
    description = "This weapon must be deployed as a 2AP action. It can be detected by anyone with a Perception of $value or higher. If a creature enters the same square as it, it detonates."
)