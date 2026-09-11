package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme

@Composable
fun LocationScreen(location: Location) {
    Column(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Text(
            text = location.name,
            style = MaterialTheme.typography.displaySmall
        )
        if (location.description.isNotBlank()) {
            Text(
                text = location.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SettlementScreen(settlement: Settlement) {
    Column(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Text(
            text = settlement.name,
            style = MaterialTheme.typography.displaySmall
        )
        if (settlement.description.isNotBlank()) {
            Text(
                text = settlement.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = "Population ${settlement.population}"
        )
        Text(
            text = "Food ${settlement.food}"
        )
    }
}

@Preview
@Composable
fun LocationScreenPreview() {
    FalloutAppTheme {
        val location = Location(0, "The Wastes", "Irradiated scrubland stretching to the horizon.")
        LocationScreen(location)
    }
}

@Preview
@Composable
fun SettlementScreenPreview() {
    FalloutAppTheme {
        val location = Settlement(1, "Honeywell", "A walled farming town built around an old water plant.")
        SettlementScreen(location)
    }
}
