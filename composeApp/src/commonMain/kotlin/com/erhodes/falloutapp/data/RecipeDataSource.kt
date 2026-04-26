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
            cost = 4,
        )
        recipeMap[1] = Recipe(
            id = 1,
            type = RecipeType.GADGET,
            itemTemplate = ItemDataSource.getItemTemplateById(3011), // Caltrops
            complexity = 6,
            cost = 4,
        )
        recipeMap[2] = Recipe(
            id = 2,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(2000), // Stimpak
            complexity = 8,
            cost = 3,
        )
        recipeMap[3] = Recipe(
            id = 3,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(2005), // Mentats
            complexity = 8,
            cost = 4,
        )
        recipeMap[4] = Recipe(
            id = 4,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(2004), // Med-X
            complexity = 9,
            cost = 3,
        )
        recipeMap[5] = Recipe(
            id = 5,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(3016), // Perfume
            complexity = 8,
            cost = 3,
        )
        recipeMap[6] = Recipe(
            id = 6,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(2008), // Radaway
            complexity = 9,
            cost = 5,
        )
        recipeMap[7] = Recipe(
            id = 7,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(2009), // Rad-X
            complexity = 8,
            cost = 2,
        )
        recipeMap[8] = Recipe(
            id = 8,
            type = RecipeType.GADGET,
            itemTemplate = ItemDataSource.getItemTemplateById(3010), // Ablative Plating
            complexity = 8,
            cost = 3,
        )
        recipeMap[9] = Recipe(
            id = 9,
            type = RecipeType.GADGET,
            itemTemplate = ItemDataSource.getItemTemplateById(3013), // Flashbang
            complexity = 8,
            cost = 3,
        )
        recipeMap[10] = Recipe(
            id = 10,
            type = RecipeType.GADGET,
            itemTemplate = ItemDataSource.getItemTemplateById(1), // Frag Grenade
            complexity = 10,
            cost = 3,
        )
        recipeMap[11] = Recipe(
            id = 11,
            type = RecipeType.GADGET,
            itemTemplate = ItemDataSource.getItemTemplateById(3019), // Smoke Grenade
            complexity = 8,
            cost = 2,
        )
        recipeMap[12] = Recipe(
            id = 12,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(2011), // Eyebite
            complexity = 8,
            cost = 3,
        )
        recipeMap[13] = Recipe(
            id = 13,
            type = RecipeType.CHEM,
            itemTemplate = ItemDataSource.getItemTemplateById(2010), // Purge
            complexity = 8,
            cost = 3,
        )
    }
}
