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
import com.erhodes.falloutapp.presentation.CharacterCreationUiState
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.agility
import falloutapp.composeapp.generated.resources.charisma
import falloutapp.composeapp.generated.resources.endurance
import falloutapp.composeapp.generated.resources.intelligence
import falloutapp.composeapp.generated.resources.luck
import falloutapp.composeapp.generated.resources.major
import falloutapp.composeapp.generated.resources.minor
import falloutapp.composeapp.generated.resources.perception
import falloutapp.composeapp.generated.resources.skills
import falloutapp.composeapp.generated.resources.special
import falloutapp.composeapp.generated.resources.strength
import falloutapp.composeapp.generated.resources.unselect
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
            SpecialAllocation(
                label = stringResource(Res.string.strength),
                value = uiState.strength,
                onIncrement = { onIncrement(0) },
                onDecrement = { onDecrement(0) }
            )
            SpecialAllocation(
                label = stringResource(Res.string.perception),
                value = uiState.perception,
                onIncrement = { onIncrement(1) },
                onDecrement = { onDecrement(1) }
            )
            SpecialAllocation(
                label = stringResource(Res.string.endurance),
                value = uiState.endurance,
                onIncrement = { onIncrement(2) },
                onDecrement = { onDecrement(2) }
            )
            SpecialAllocation(
                label = stringResource(Res.string.charisma),
                value = uiState.charisma,
                onIncrement = { onIncrement(3) },
                onDecrement = { onDecrement(3) }
            )
            SpecialAllocation(
                label = stringResource(Res.string.intelligence),
                value = uiState.intelligence,
                onIncrement = { onIncrement(4) },
                onDecrement = { onDecrement(4) }
            )
            SpecialAllocation(
                label = stringResource(Res.string.agility),
                value = uiState.agility,
                onIncrement = { onIncrement(5) },
                onDecrement = { onDecrement(5) }
            )
            SpecialAllocation(
                label = stringResource(Res.string.luck),
                value = uiState.luck,
                onIncrement = { onIncrement(6) },
                onDecrement = { onDecrement(6) }
            )

        }

        Text(stringResource(Res.string.skills))
        Text("Majors remaining: ${uiState.majorsRemaining}  Minors remaining: ${uiState.minorsRemaining}")
//        val ordinal = 0
//        Row {
//            Text(stringResource(Skills.entries[ordinal].description))
//            Button(
//                onClick = { },
//                enabled = uiState.skills[ordinal] == 5
//            ) {
//                Text(stringResource(Res.string.major))
//            }
//            Button(
//                onClick = {},
//                enabled = uiState.skills[ordinal] == 4
//            ) {
//                Text(stringResource(Res.string.minor))
//            }
//        }

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
fun SpecialAllocation(label: String, value: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Column(
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Text(label)
        Text("$value")
        Row {
            Button(
                enabled = value != 3,
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