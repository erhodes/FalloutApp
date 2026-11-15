package com.erhodes.falloutapp.model.effect

import com.erhodes.falloutapp.model.Character

class SpeedEffect: Effect() {
    override fun apply(character: Character) {
        character.bonusSpeed+=2
    }

    override fun remove(character: Character) {
        character.bonusSpeed-=2
    }
}