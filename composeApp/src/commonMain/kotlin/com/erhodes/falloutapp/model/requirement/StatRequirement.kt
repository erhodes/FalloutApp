package com.erhodes.falloutapp.model.requirement

import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Stats

class StatRequirement(val stat: Stats, min: Int): Requirement(min) {
    override fun qualifiedForByCharacter(character: Character): Boolean {
        return (character.getStatByOrdinal(stat.ordinal) >= min)
    }
}