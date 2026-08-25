package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.campaign.Campaign

@Composable
fun CampaignScreen(campaign: Campaign, onActiveEncounterClicked: () -> Unit, onActiveLocationClicked: () -> Unit) {
    Column {
        Text(
            text = campaign.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            onClick = onActiveEncounterClicked
        ) {
            Text("Active Encounter")
        }
        Button(
            onClick = onActiveLocationClicked
        ) {
            Text("Active Location")
        }
    }
}