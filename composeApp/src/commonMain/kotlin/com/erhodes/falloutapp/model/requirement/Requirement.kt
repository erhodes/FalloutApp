package com.erhodes.falloutapp.model.requirement

import com.erhodes.falloutapp.model.Character

abstract class Requirement(
    val min: Int,
) {
    abstract fun qualifiedForByCharacter(character: Character): Boolean
}