package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme

@Composable
fun LocationScreen(location: Location) {
    Text(
        text = location.name,
        style = MaterialTheme.typography.displaySmall
    )
}

@Composable
fun SettlementScreen(settlement: Settlement) {
    Column {
        Text(
            text = settlement.name,
            style = MaterialTheme.typography.displaySmall
        )
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
        val location = Location("The Wastes")
        LocationScreen(location)
    }
}

@Preview
@Composable
fun SettlementScreenPreview() {
    FalloutAppTheme {
        val location = Settlement("Honeywell")
        SettlementScreen(location)
    }
}