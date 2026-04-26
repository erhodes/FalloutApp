package com.erhodes.falloutapp.model.ability

class Knockback(val value: Int) : Ability(
    title = "Knockback $value",
    description = "Forces the target $value away from you in a straight line. If they collide with another creature, both take the damage of the attack. If they collide with an obstacle, they take 1 additional damage."
)