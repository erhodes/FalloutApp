package com.erhodes.falloutapp.model.action

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats

abstract class Action(
    val title: String,
    val stat: Stats?,
    val skill: Skills?,
    val effectText: String,
) {
    fun testValue(character: Character): Int {
        val s = stat ?: return 0
        val k = skill ?: return 0
        return character.getStatByOrdinal(s.ordinal) + character.skills[k.ordinal]
    }

    fun isTested(): Boolean = stat != null && skill != null
}
