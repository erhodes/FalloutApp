package com.erhodes.falloutapp.model.effect

import com.erhodes.falloutapp.model.Character

open abstract class Effect {
    abstract fun apply(character: Character)

    abstract fun remove(character: Character)
}