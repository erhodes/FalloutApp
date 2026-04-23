package com.erhodes.falloutapp.model.ability

class AbilityGroup(val abilities: List<Ability>) : Ability(
    title = abilities.joinToString(", ") { it.title },
    description = abilities.joinToString("\n\n") { "${it.title}: ${it.description}" }
)
