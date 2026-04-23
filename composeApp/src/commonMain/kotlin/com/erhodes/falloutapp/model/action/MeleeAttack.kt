package com.erhodes.falloutapp.model.action

import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.model.Weapon

class MeleeAttack(weapon: Weapon): Attack(Stats.STRENGTH, Skills.MELEE, weapon)