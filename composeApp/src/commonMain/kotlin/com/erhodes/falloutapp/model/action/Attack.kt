package com.erhodes.falloutapp.model.action

import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.model.ability.NoAbility

abstract class Attack(stat: Stats, skill: Skills, weapon: Weapon) : Action(
    title = "Attack",
    stat = stat,
    skill = skill,
    effectText = buildAttackEffectText(weapon),
)

private fun buildAttackEffectText(weapon: Weapon): String {
    val base = "Range ${weapon.range}. Damage ${weapon.damage.joinToString("/")}."
    if (weapon.ability.all { it is NoAbility }) return base
    val abilities = weapon.ability.joinToString("/") { if (it is NoAbility) "-" else it.title }
    return "$base Abilities $abilities."
}
