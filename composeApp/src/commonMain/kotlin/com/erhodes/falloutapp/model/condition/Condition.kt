package com.erhodes.falloutapp.model.condition

import kotlinx.serialization.Serializable

@Serializable
sealed class Condition {
    abstract val template: ConditionTemplate

    companion object {
        fun buildNewCondition(template: ConditionTemplate): Condition {
            return if (template.scalable) {
                ScalableCondition(template, 1)
            } else {
                BasicCondition(template)
            }
        }
    }
}

@Serializable
class BasicCondition(override val template: ConditionTemplate) : Condition()

@Serializable
class ScalableCondition(
    override val template: ConditionTemplate, var count: Int) : Condition()
