package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.presentation.CharacterCreationUiState
import falloutapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterCreationScreen(
    uiState: CharacterCreationUiState,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
    onMajorClicked: (Int) -> Unit,
    onMinorClicked: (Int) -> Unit,
    onComplete: (String) -> Unit
) {
    Column {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Name") }
        )

        Text(stringResource(Res.string.special))
        Text("Points remaining ${uiState.pointsRemaining}/7")

        Row {
            for (i in 0..6) {
                SpecialAllocation(
                    label = stringResource(Stats.entries[i].displayName),
                    value = uiState.stats[i],
                    pointsRemaining = uiState.pointsRemaining > 0,
                    onIncrement = { onIncrement(i) },
                    onDecrement = { onDecrement(i) }
                )
            }
        }

        Text(stringResource(Res.string.skills))
        Text("Majors remaining: ${uiState.majorsRemaining}  Minors remaining: ${uiState.minorsRemaining}")

        Row {
            Column(
                modifier = Modifier.padding(end = 10.dp)
            ) {
                for (i in 0.. 5) {
                    SkillAllocationPanel(i, uiState.skills[i], onMajorClicked, onMinorClicked)
                }
            }
            Column {
                for (i in 6.. 11) {
                    SkillAllocationPanel(i, uiState.skills[i], onMajorClicked, onMinorClicked)
                }
            }
        }

        Button(
            onClick = { onComplete(text) },
            enabled = text.isNotEmpty()
        ) {
            Text("Done")
        }
    }
}

@Composable
fun SkillAllocationPanel(ordinal: Int, value: Int, onSelectMajor: (Int) -> Unit, onSelectMinor: (Int) -> Unit) {
    Row {
        Text(stringResource(Skills.entries[ordinal].description))
        Button(
            onClick = { onSelectMajor(ordinal) }
        ) {
            if (value == 5) {
                Text(stringResource(Res.string.unselect))
            } else {
                Text(stringResource(Res.string.major))
            }

        }
        Button(
            onClick = { onSelectMinor(ordinal) }
        ) {
            if (value == 4) {
                Text(stringResource(Res.string.unselect))
            } else {
                Text(stringResource(Res.string.minor))
            }
        }
    }
}

@Composable
fun SpecialAllocation(label: String, value: Int, pointsRemaining: Boolean, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Column(
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Text(label)
        Text("$value")
        Row {
            Button(
                enabled = (value != 3 && pointsRemaining),
                onClick = onIncrement
            ) {
                Text("+")
            }
            Button(
                enabled = value != 1,
                onClick = onDecrement
            ) {
                Text("-")
            }
        }
    }
}

@Preview
@Composable
fun CharacterCreationScreenPreview() {
    MaterialTheme {
        CharacterCreationScreen(
            CharacterCreationUiState(),
            {},
            {},
            {},
            {}
        ) {  }
    }
}