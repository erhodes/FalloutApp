package com.erhodes.falloutapp.model.condition

import com.erhodes.falloutapp.model.condition.ConditionTemplateSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ConditionTemplateSerializer::class)
enum class ConditionTemplate(val title: String, val description: String, val scalable: Boolean) {
    Bloodied("Bloodied", "You have only 1 AP", false),
    Burning("Burning", "At the end of your turn, lower your Burning by 1 and suffer 1 damage (ignoring Toughness).", true),
    Immobilized("Immobilized", "You can't move.", true),
    Shredded("Shredded", "Your defenses are lowered. An attacker may lower this condition by 1 to reroll a dice.", true)
}