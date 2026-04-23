package com.erhodes.falloutapp.model

import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.data.RecipeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = RecipeSerializer::class)
class Recipe(
    val id: Int,
    val type: RecipeType,
    val itemTemplate: ItemTemplate,
    val complexity: Int,
    val cost: Int,
)

enum class RecipeType(val skill: Skills, val costItemId: Int) {
    CHEM(Skills.SCIENCE, ItemDataSource.ID_MEDICAL_SUPPLIES),
    GADGET(Skills.ENGINEERING, ItemDataSource.ID_PARTS),
}
