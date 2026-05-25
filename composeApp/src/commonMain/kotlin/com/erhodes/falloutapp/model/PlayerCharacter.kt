package com.erhodes.falloutapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("player")
class PlayerCharacter(
    override var name: String = "",
    override val ownerId: String = "",
    override val strength: Int = 1,
    override val perception: Int = 1,
    override val endurance: Int = 1,
    override val charisma: Int = 1,
    override val agility: Int = 1,
    override val intelligence: Int = 1,
    override val luck: Int = 1,
    override val skills: ArrayList<Int> = arrayListOf<Int>(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
) : Character() {

    companion object {
        const val MAX_STRESS = 10
    }

    var level = 1
    var milestones = 0
    var stress = 0

    val recipes = HashSet<Recipe>()

    fun gainMilestone() {
        milestones++
        if (milestones >= 3) {
            level++
            milestones = 0
        }
    }

    fun getMaxSkillValue(): Int {
        return 5 + level / 2
    }

    fun gainPerk(perk: Perk) {
        if (perks.size > level) return
        perks.add(perk)
        perk.effect?.apply(this)
    }

    fun removePerk(perk: Perk) {
        perks.remove(perk)
        perk.effect?.remove(this)
    }

    fun qualifiesForPerk(perk: Perk): Boolean {
        perk.requirements.forEach { requirement ->
            if (!requirement.qualifiedForByCharacter(this)) return false
        }
        return true
    }

    fun modifyStress(amount: Int) {
        if (stress + amount !in 0..MAX_STRESS) return
        stress += amount
    }

    fun canLearnRecipe(recipe: Recipe): Boolean =
        recipes.size < intelligence
            && recipe !in recipes
            && intelligence + skills[recipe.type.skill.ordinal] >= recipe.complexity

    fun learnRecipe(recipe: Recipe): Boolean {
        if (!canLearnRecipe(recipe)) return false
        recipes.add(recipe)
        return true
    }

    fun canCraftRecipe(recipe: Recipe): Boolean {
        if (recipe !in recipes) return false
        val available = (inventory + loadout)
            .filterIsInstance<StackableItem>()
            .filter { it.template.id == recipe.type.costItemId }
            .sumOf { it.count }
        return available >= recipe.cost
    }

    fun consumeCraftingCost(recipe: Recipe): Boolean {
        if (!canCraftRecipe(recipe)) return false
        var remaining = recipe.cost
        val stacks = (inventory + loadout)
            .filterIsInstance<StackableItem>()
            .filter { it.template.id == recipe.type.costItemId }
        for (stack in stacks) {
            if (remaining <= 0) break
            val take = kotlin.math.min(stack.count, remaining)
            decreaseStackCountForItem(stack, take)
            remaining -= take
        }
        return true
    }
}
