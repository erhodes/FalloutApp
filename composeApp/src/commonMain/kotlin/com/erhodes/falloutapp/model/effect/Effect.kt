package com.erhodes.falloutapp.model.effect

import com.erhodes.falloutapp.model.Character

abstract class Effect {
    abstract fun apply(character: Character)

    abstract fun remove(character: Character)
}