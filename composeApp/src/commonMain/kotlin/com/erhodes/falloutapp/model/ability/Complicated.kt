package com.erhodes.falloutapp.model.ability

class Complicated(val value: Int) : Ability(
    title = "Complicated $value",
    description = "Repairing this armor requires $value Parts per Durability, instead of just 1."
)