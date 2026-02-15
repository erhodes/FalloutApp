package com.erhodes.falloutapp.model.ability

class Knockback(val value: Int) : Ability(
    title = "Knockback $value",
    description = "Forces the target $value away from you in a straight line."
)