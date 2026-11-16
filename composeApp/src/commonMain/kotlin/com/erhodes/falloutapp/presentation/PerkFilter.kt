package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.model.requirement.Requirement
import com.erhodes.falloutapp.model.requirement.SkillRequirement
import com.erhodes.falloutapp.model.requirement.StatRequirement
import com.erhodes.falloutapp.model.requirement.VariableSkillRequirement

/** Tier choices for the perk filter. [tier] is null for "All". */
enum class TierFilter(val tier: Int?) {
    ONE(1),
    TWO(2),
    ALL(null)
}

/**
 * Criteria for narrowing the perk list on the perk select screen.
 * Add new criteria as fields and AND them together in [matches].
 *
 * @param stats When non-empty, only perks with a [StatRequirement] on at least one of these stats match.
 * @param skills When non-empty, only perks with a skill requirement on at least one of these skills match.
 *   A [VariableSkillRequirement] counts if either of its skills is selected.
 */
data class PerkFilter(
    val tier: TierFilter = TierFilter.ALL,
    val stats: Set<Stats> = emptySet(),
    val skills: Set<Skills> = emptySet(),
) {
    fun matches(perk: Perk): Boolean {
        // Tier -1 perks are never selectable, regardless of filter.
        if (perk.tier == -1) return false
        if (tier.tier != null && perk.tier != tier.tier) return false
        if (stats.isNotEmpty() && perk.requirements.none { it is StatRequirement && it.stat in stats }) return false
        if (skills.isNotEmpty() && perk.requirements.none { it.usesAnySkill(skills) }) return false
        return true
    }
}

private fun Requirement.usesAnySkill(skills: Set<Skills>): Boolean = when (this) {
    is SkillRequirement -> skill in skills
    is VariableSkillRequirement -> skillA in skills || skillB in skills
    else -> false
}
