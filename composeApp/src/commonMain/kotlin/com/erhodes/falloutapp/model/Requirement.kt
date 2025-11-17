package com.erhodes.falloutapp.model

abstract class Requirement(
    val min: Int,
) {
    abstract fun qualifiedForByCharacter(character: Character): Boolean
}