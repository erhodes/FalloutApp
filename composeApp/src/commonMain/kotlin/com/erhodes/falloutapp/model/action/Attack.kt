package com.erhodes.falloutapp.model.action

import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.model.Weapon

abstract class Attack(stat: Stats, skill: Skills, weapon: Weapon) : Action(
    title = "Attack",
    stat = stat,
    skill = skill,
    effectText = "Range ${weapon.range}. Damage ${weapon.damage.joinToString("/")}.",
)
