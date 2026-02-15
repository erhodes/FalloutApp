package com.erhodes.falloutapp.model.ability

class Shred(val value: Int) : Ability(
    title = "Shred $value",
    description = "Inflicts $value stacks of Vulnerable. When attacking a creature with Vulnerable, the attacker may remove 1 stack to reroll a die."
)