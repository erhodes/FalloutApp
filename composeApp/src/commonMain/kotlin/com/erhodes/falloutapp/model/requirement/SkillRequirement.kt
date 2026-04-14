package com.erhodes.falloutapp.model.requirement

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills

class SkillRequirement(val skill: Skills, min: Int): Requirement(min) {
    override fun qualifiedForByCharacter(character: Character): Boolean {
        return (character.skills[skill.ordinal] >= min)
    }
}