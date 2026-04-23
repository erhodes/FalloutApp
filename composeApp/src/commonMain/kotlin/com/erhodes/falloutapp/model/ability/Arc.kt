package com.erhodes.falloutapp.model.ability

class Arc(val value: Int) : Ability(
    title = "Arc $value",
    description = "This attack can chain between up to ${value} targets. Resolve damage to one target at a time, starting with the primary. Each target must be within 5 of the previous target."
)