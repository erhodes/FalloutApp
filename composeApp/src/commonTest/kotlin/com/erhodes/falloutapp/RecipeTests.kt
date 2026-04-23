package com.erhodes.falloutapp

import com.erhodes.falloutapp.data.RecipeDataSource
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RecipeTests {

    @Test
    fun learnRecipeAddsToSet() {
        val character = Character("Bob", intelligence = 5)
        character.skills[Skills.SCIENCE.ordinal] = 3
        val buffout = RecipeDataSource.getRecipeById(0)

        assertTrue(character.learnRecipe(buffout))
        assertTrue(buffout in character.recipes)
        assertEquals(1, character.recipes.size)
    }

    @Test
    fun removeRecipeFromSet() {
        val character = Character("Bob", intelligence = 5)
        character.skills[Skills.ENGINEERING.ordinal] = 1
        val caltrops = RecipeDataSource.getRecipeById(1)

        character.learnRecipe(caltrops)
        assertEquals(1, character.recipes.size)

        character.recipes.remove(caltrops)
        assertEquals(0, character.recipes.size)
        assertFalse(caltrops in character.recipes)
    }

    @Test
    fun cannotLearnSameRecipeTwice() {
        val character = Character("Bob", intelligence = 5)
        character.skills[Skills.SCIENCE.ordinal] = 3
        val buffout = RecipeDataSource.getRecipeById(0)

        assertTrue(character.learnRecipe(buffout))
        assertFalse(character.learnRecipe(buffout))
        assertEquals(1, character.recipes.size)
    }

    @Test
    fun cannotExceedIntelligenceCap() {
        val character = Character("Bob", intelligence = 1)
        character.skills[Skills.SCIENCE.ordinal] = 8
        character.skills[Skills.ENGINEERING.ordinal] = 8
        val buffout = RecipeDataSource.getRecipeById(0)
        val caltrops = RecipeDataSource.getRecipeById(1)

        assertTrue(character.canLearnRecipe(buffout))
        assertTrue(character.canLearnRecipe(caltrops))
        
        assertTrue(character.learnRecipe(buffout))

        assertFalse(character.canLearnRecipe(caltrops))
        assertFalse(character.learnRecipe(caltrops))
        assertEquals(1, character.recipes.size)
    }

    @Test
    fun skillRequirementEnforced() {
        // Buffout complexity = 8, needs intelligence + SCIENCE >= 8
        val character = Character("Bob", intelligence = 5)
        val buffout = RecipeDataSource.getRecipeById(0)

        // default skills = 2, so 5 + 2 = 7 < 8
        assertFalse(character.canLearnRecipe(buffout))
        assertFalse(character.learnRecipe(buffout))
        assertEquals(0, character.recipes.size)

        character.skills[Skills.SCIENCE.ordinal] = 3
        assertTrue(character.canLearnRecipe(buffout))
        assertTrue(character.learnRecipe(buffout))
    }

    @Test
    fun skillRequirementUsesRecipeTypeSkill() {
        // Caltrops is a GADGET recipe, so it checks ENGINEERING, not SCIENCE.
        // Complexity = 6: intelligence + ENGINEERING >= 6.
        val character = Character("Bob", intelligence = 5)
        character.skills[Skills.SCIENCE.ordinal] = 10
        character.skills[Skills.ENGINEERING.ordinal] = 0
        val caltrops = RecipeDataSource.getRecipeById(1)

        assertFalse(character.canLearnRecipe(caltrops))

        character.skills[Skills.ENGINEERING.ordinal] = 1
        assertTrue(character.canLearnRecipe(caltrops))
        assertTrue(character.learnRecipe(caltrops))
    }
}
