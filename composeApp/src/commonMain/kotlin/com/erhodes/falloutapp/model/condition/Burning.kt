package com.erhodes.falloutapp.model.condition

class Burning(var value: Int): Condition() {
    override fun toString(): String {
        return "Burning $value"
    }
}