package com.erhodes.falloutapp.model.ability

class Line(val value: Int): Ability(
    title = "Line $value",
    description = "Attacks with this weapon target all creatures in a 1x$value line."
)