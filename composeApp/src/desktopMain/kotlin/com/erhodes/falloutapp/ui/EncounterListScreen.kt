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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EncounterListScreen(
    encounters: List<Encounter>,
    onSelect: (Encounter) -> Unit,
    onNewEncounter: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Button(onClick = onNewEncounter) {
            Text("New Encounter")
        }
        Text(
            text = "Saved Encounters",
            style = MaterialTheme.typography.displaySmall
        )
        if (encounters.isEmpty()) {
            Text("No saved encounters.")
        }
        encounters.forEach { encounter ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(encounter.name)
                Button(onClick = { onSelect(encounter) }) {
                    Text("Select")
                }
            }
        }
    }
}

@Preview
@Composable
fun EncounterListScreenPreview() {
    EncounterListScreen(
        encounters = listOf(
            Encounter("Raider Ambush"),
            Encounter("Ghoul Horde")
        ),
        onSelect = {},
        onNewEncounter = {}
    )
}
