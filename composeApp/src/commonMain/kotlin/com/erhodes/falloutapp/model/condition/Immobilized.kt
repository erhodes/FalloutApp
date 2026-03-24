package com.erhodes.falloutapp.model.condition

class Immobilized(var value: Int): Condition() {
    override fun toString(): String {
        return "Immobilized $value"
    }
}