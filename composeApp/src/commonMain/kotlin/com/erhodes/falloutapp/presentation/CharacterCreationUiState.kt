package com.erhodes.falloutapp.presentation

data class CharacterCreationUiState(
    val pointsRemaining: Int = 7,
    val majorsRemaining: Int = 2,
    val minorsRemaining: Int = 3,
    val stats: ArrayList<Int> = arrayListOf<Int>(1,1,1,1,1,1,1),
    val skills: ArrayList<Int> = arrayListOf<Int>(2,2,2,2,2,2,2,2,2,2,2,2)
)