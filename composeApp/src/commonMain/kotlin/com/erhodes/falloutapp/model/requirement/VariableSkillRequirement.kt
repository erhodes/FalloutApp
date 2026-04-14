package com.erhodes.falloutapp.model.requirement

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills

/**
 * Used when there are two possible skills that can be used as the requirement.
 */
class VariableSkillRequirement(val skillA: Skills, val skillB: Skills, min: Int): Requirement(min) {
    override fun qualifiedForByCharacter(character: Character): Boolean {
        return (character.skills[skillA.ordinal] >= min || character.skills[skillB.ordinal] >= min)
    }
}