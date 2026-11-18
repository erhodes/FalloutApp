package com.erhodes.falloutapp

import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.model.requirement.SkillRequirement
import com.erhodes.falloutapp.model.requirement.StatRequirement
import com.erhodes.falloutapp.model.requirement.VariableSkillRequirement
import com.erhodes.falloutapp.presentation.PerkFilter
import com.erhodes.falloutapp.presentation.TierFilter
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PerkFilterTests {
    private val hidden = Perk("Hidden", "", 100, tier = -1)
    private val tierOne = Perk("One", "", 101, tier = 1)
    private val tierTwo = Perk("Two", "", 102, tier = 2)
    private val tierThree = Perk("Three", "", 111, tier = 3)

    private val agilityPerk = Perk("Agile", "", 103, tier = 1,
        requirements = arrayOf(StatRequirement(Stats.AGILITY, 2), SkillRequirement(Skills.GUNS, 5)))
    private val intelligencePerk = Perk("Smart", "", 104, tier = 1,
        requirements = arrayOf(StatRequirement(Stats.INTELLIGENCE, 2)))
    private val skillOnlyPerk = Perk("Skilled", "", 105, tier = 1,
        requirements = arrayOf(SkillRequirement(Skills.SCIENCE, 5)))
    private val agilityTierTwo = Perk("Agile II", "", 106, tier = 2,
        requirements = arrayOf(StatRequirement(Stats.AGILITY, 3)))
    private val hiddenAgility = Perk("Hidden Agile", "", 107, tier = -1,
        requirements = arrayOf(StatRequirement(Stats.AGILITY, 2)))

    @Test
    fun tierMinusOneNeverMatches() {
        TierFilter.entries.forEach {
            assertFalse(PerkFilter(it).matches(hidden), "tier -1 shown under $it")
        }
    }

    @Test
    fun tierOneMatchesOnlyTierOne() {
        val filter = PerkFilter(TierFilter.ONE)
        assertTrue(filter.matches(tierOne))
        assertFalse(filter.matches(tierTwo))
        assertFalse(filter.matches(tierThree))
    }

    @Test
    fun tierTwoMatchesOnlyTierTwo() {
        val filter = PerkFilter(TierFilter.TWO)
        assertTrue(filter.matches(tierTwo))
        assertFalse(filter.matches(tierOne))
        assertFalse(filter.matches(tierThree))
    }

    @Test
    fun tierThreeMatchesOnlyTierThree() {
        val filter = PerkFilter(TierFilter.THREE)
        assertTrue(filter.matches(tierThree))
        assertFalse(filter.matches(tierTwo))
        assertFalse(filter.matches(tierOne))
    }

    @Test
    fun allMatchesEveryRealTier() {
        val filter = PerkFilter()
        assertTrue(filter.matches(tierOne))
        assertTrue(filter.matches(tierTwo))
        assertTrue(filter.matches(tierThree))
    }

    @Test
    fun noStatsSelectedIgnoresStatFilter() {
        val filter = PerkFilter()
        listOf(tierOne, agilityPerk, intelligencePerk, skillOnlyPerk).forEach {
            assertTrue(filter.matches(it), "${it.name} hidden with no stats selected")
        }
    }

    @Test
    fun singleStatMatchesOnlyPerksRequiringIt() {
        val filter = PerkFilter(stats = setOf(Stats.AGILITY))
        assertTrue(filter.matches(agilityPerk))
        assertFalse(filter.matches(intelligencePerk))
        assertFalse(filter.matches(skillOnlyPerk))
        assertFalse(filter.matches(tierOne))
    }

    @Test
    fun multipleStatsMatchAny() {
        val filter = PerkFilter(stats = setOf(Stats.AGILITY, Stats.INTELLIGENCE))
        assertTrue(filter.matches(agilityPerk))
        assertTrue(filter.matches(intelligencePerk))
        assertFalse(filter.matches(skillOnlyPerk))
    }

    @Test
    fun statsAndTierCombine() {
        val filter = PerkFilter(TierFilter.ONE, setOf(Stats.AGILITY))
        assertTrue(filter.matches(agilityPerk))
        assertFalse(filter.matches(agilityTierTwo))
    }

    @Test
    fun tierMinusOneHiddenEvenWithMatchingStat() {
        assertFalse(PerkFilter(stats = setOf(Stats.AGILITY)).matches(hiddenAgility))
    }

    private val sciencePerk = Perk("Scientist", "", 108, tier = 1,
        requirements = arrayOf(SkillRequirement(Skills.SCIENCE, 5)))
    private val variablePerk = Perk("Brawler", "", 109, tier = 1,
        requirements = arrayOf(VariableSkillRequirement(Skills.MELEE, Skills.THROWING, 5)))
    private val hiddenScience = Perk("Hidden Scientist", "", 110, tier = -1,
        requirements = arrayOf(SkillRequirement(Skills.SCIENCE, 5)))

    @Test
    fun noSkillsSelectedIgnoresSkillFilter() {
        val filter = PerkFilter()
        listOf(tierOne, sciencePerk, variablePerk, intelligencePerk).forEach {
            assertTrue(filter.matches(it), "${it.name} hidden with no skills selected")
        }
    }

    @Test
    fun singleSkillMatchesOnlyPerksRequiringIt() {
        // agilityPerk requires Guns; sciencePerk requires Science; intelligencePerk has no skill requirement.
        val filter = PerkFilter(skills = setOf(Skills.GUNS))
        assertTrue(filter.matches(agilityPerk))
        assertFalse(filter.matches(sciencePerk))
        assertFalse(filter.matches(intelligencePerk))
    }

    @Test
    fun multipleSkillsMatchAny() {
        val filter = PerkFilter(skills = setOf(Skills.GUNS, Skills.SCIENCE))
        assertTrue(filter.matches(agilityPerk))
        assertTrue(filter.matches(sciencePerk))
        assertFalse(filter.matches(variablePerk))
    }

    @Test
    fun variableSkillRequirementMatchesEitherSkill() {
        assertTrue(PerkFilter(skills = setOf(Skills.MELEE)).matches(variablePerk))
        assertTrue(PerkFilter(skills = setOf(Skills.THROWING)).matches(variablePerk))
        assertFalse(PerkFilter(skills = setOf(Skills.GUNS)).matches(variablePerk))
    }

    @Test
    fun skillsAndStatsCombine() {
        // agilityPerk requires Agility + Guns, so Science excludes it even though Agility matches.
        assertFalse(PerkFilter(stats = setOf(Stats.AGILITY), skills = setOf(Skills.SCIENCE)).matches(agilityPerk))
        assertTrue(PerkFilter(stats = setOf(Stats.AGILITY), skills = setOf(Skills.GUNS)).matches(agilityPerk))
    }

    @Test
    fun tierMinusOneHiddenEvenWithMatchingSkill() {
        assertFalse(PerkFilter(skills = setOf(Skills.SCIENCE)).matches(hiddenScience))
    }
}
