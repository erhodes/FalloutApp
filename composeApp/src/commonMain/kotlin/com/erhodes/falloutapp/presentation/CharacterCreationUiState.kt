package com.erhodes.falloutapp.presentation

data class CharacterCreationUiState(
    val pointsRemaining: Int = 7,
    val strength: Int = 1,
    val perception: Int = 1,
    val endurance: Int = 1,
    val charisma: Int = 1,
    val intelligence: Int = 1,
    val agility: Int = 1,
    val luck: Int = 1,
    val majorsRemaining: Int = 2,
    val minorsRemaining: Int = 3,
    val skills: ArrayList<Int> = arrayListOf<Int>(2,2,2,2,2,2,2,2,2,2,2,2)
)