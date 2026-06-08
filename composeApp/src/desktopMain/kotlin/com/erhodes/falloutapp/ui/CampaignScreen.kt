package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.Campaign
import com.erhodes.falloutapp.presentation.CampaignUiState
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CampaignScreen(campaignUiState: CampaignUiState, onEncountersClicked: () -> Unit) {
    val campaign = campaignUiState.campaign
    Column {
        Text(
            text = campaign.name,
            style = MaterialTheme.typography.h3
        )
        Text(
            text = "Active Players",
            style = MaterialTheme.typography.h5
        )
        campaignUiState.players.forEach { row ->
            Text("${row.ownerName} — ${row.characterName}")
        }
        Button(
            onClick = onEncountersClicked
        ) {
            Text("Encounters")
        }
    }
}

@Preview
@Composable
fun CampaignScreenPreview() {
    val campaign = Campaign("Demo", "1")
    FalloutAppTheme {
        CampaignScreen(CampaignUiState(campaign), onEncountersClicked = {})
    }
}