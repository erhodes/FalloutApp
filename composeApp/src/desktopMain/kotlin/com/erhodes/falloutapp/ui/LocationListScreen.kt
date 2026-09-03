package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.collections.forEach

@Composable
fun LocationListScreen(
    locations: List<Location>,
    activeLocation: Location?,
    onSelect: (Location) -> Unit,
    onSetActive: (Location) -> Unit,
    onNewLocation: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Button(onClick = onNewLocation) {
            Text("New Location")
        }
        Text(
            text = "Saved Locations",
            style = MaterialTheme.typography.displaySmall
        )
        if (locations.isEmpty()) {
            Text("No saved locations.")
        }
        locations.forEach { location ->
            val isActive = location.id == activeLocation?.id
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(location.name)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { onSelect(location) }) {
                        Text("Select")
                    }
                    Button(
                        onClick = { onSetActive(location) },
                        enabled = !isActive
                    ) {
                        Text(if (isActive) "Active" else "Set Active")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LocationListScreenPreview() {
    val ruins = Location(1, "Ruins")
    LocationListScreen(
        locations = listOf(
            Settlement(0, "Stillwater"),
            ruins
        ),
        activeLocation = ruins,
        onSelect = {},
        onSetActive = {},
        onNewLocation = {}
    )
}
