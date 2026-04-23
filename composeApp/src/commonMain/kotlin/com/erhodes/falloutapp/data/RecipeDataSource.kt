package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Recipe
import com.erhodes.falloutapp.model.RecipeType

object RecipeDataSource {
    val recipeMap: HashMap<Int, Recipe> = HashMap()
    fun getRecipeById(id: Int): Recipe {
        return recipeMap[id]!!
    }
    init {
        recipeMap[0] = Recipe(
            id = 0,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(2002), // Buffout
            complexity = 8,
            cost = 8,
        )
        recipeMap[1] = Recipe(
            id = 1,
            type = RecipeType.GADGET,
            itemTemplate = ItemDataSource.getItemTemplateById(3011), // Caltrops
            complexity = 6,
            cost = 4,
        )
    }
}
