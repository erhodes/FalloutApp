package com.erhodes.falloutapp.model.ability

class Defensive(val value: Int): Ability(
    title = "Defensive",
    description = "You may suffer 1 Stress to prevent $value damage from a melee attack."
)