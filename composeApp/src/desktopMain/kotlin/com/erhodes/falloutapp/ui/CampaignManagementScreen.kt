package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.campaign.Campaign
import com.erhodes.falloutapp.presentation.CampaignUiState
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CampaignManagementScreen(campaignUiState: CampaignUiState, onEncountersClicked: () -> Unit, onLocationsClicked: () -> Unit) {
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
        Button(
            onClick = onLocationsClicked
        ) {
            Text("Locations")
        }
    }
}

@Preview
@Composable
fun CampaignManagementScreenPreview() {
    val campaign = Campaign("Demo", "1")
    FalloutAppTheme {
        CampaignManagementScreen(CampaignUiState(campaign), onEncountersClicked = {}, onLocationsClicked = {})
    }
}