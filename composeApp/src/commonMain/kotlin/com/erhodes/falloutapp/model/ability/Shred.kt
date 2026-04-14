package com.erhodes.falloutapp.model.ability

class Shred(val value: Int) : Ability(
    title = "Shred $value",
    description = "Inflicts $value stacks of Shredded. When attacking a creature with Shredded, the attacker may remove 1 stack to reroll a die."
)