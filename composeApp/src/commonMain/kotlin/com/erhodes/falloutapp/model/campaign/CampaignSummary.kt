package com.erhodes.falloutapp.model.campaign

import kotlinx.serialization.Serializable

@Serializable
data class CampaignSummary(val name: String, val id: String) {
}