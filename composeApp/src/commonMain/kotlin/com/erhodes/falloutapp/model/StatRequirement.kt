package com.erhodes.falloutapp.model

class StatRequirement(val stat: Stats, min: Int): Requirement(min) {
    override fun qualifiedForByCharacter(character: Character): Boolean {
        return (character.getStatByOrdinal(stat.ordinal) >= min)
    }
}