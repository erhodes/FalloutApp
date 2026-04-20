package com.erhodes.falloutapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EncounterScreen(encounter: Encounter, onAddEnemyClicked: () -> Unit) {
    Column(
        modifier = Modifier.padding(start = Dimens.paddingMedium)
    ) {
        Text(
            text = encounter.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Characters:",
            style = MaterialTheme.typography.headlineSmall
        )
        val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }
        encounter.characters.forEachIndexed { index, character ->
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .clickable {
                        expandedStates[index] = !(expandedStates[index] ?: false)
                    }
            )
            if (expandedStates[index] == true) {
                EnemyCharacterDisplay(character)
            }
        }
        Button(
            onClick = onAddEnemyClicked
        ) {
            Text("Add Enemy")
        }
    }
}

@Preview
@Composable
fun EncounterScreenPreview() {
    FalloutAppTheme {
        val encounter = Encounter("Test")
        encounter.addCharacter(Character("Bob"))
        encounter.addCharacter(Character("Deathclaw"))
        EncounterScreen(
            encounter,
            {}
        )
    }
}