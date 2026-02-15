package com.erhodes.falloutapp.model.ability

class Blast(val value: Int) : Ability(
    title = "Blast $value",
    description = "Attacks with this weapon affect all creatures within X of a targeted square."
)