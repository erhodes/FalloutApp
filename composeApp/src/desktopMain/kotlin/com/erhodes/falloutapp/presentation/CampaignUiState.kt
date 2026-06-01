package com.erhodes.falloutapp.presentation

import com.erhodes.falloutapp.model.Campaign

data class CampaignPlayerRow(
    val ownerName: String,
    val characterName: String,
)

class CampaignUiState(
    val campaign: Campaign,
    val players: List<CampaignPlayerRow> = emptyList(),
)