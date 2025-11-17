package com.erhodes.falloutapp.model

class SkillRequirement(val skill: Skills, min: Int): Requirement(min) {
    override fun qualifiedForByCharacter(character: Character): Boolean {
        return (character.skills[skill.ordinal] >= min)
    }
}