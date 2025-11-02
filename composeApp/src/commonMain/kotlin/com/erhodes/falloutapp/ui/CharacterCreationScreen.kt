package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.erhodes.falloutapp.presentation.CharacterCreationUiState
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.agility
import falloutapp.composeapp.generated.resources.charisma
import falloutapp.composeapp.generated.resources.endurance
import falloutapp.composeapp.generated.resources.intelligence
import falloutapp.composeapp.generated.resources.luck
import falloutapp.composeapp.generated.resources.perception
import falloutapp.composeapp.generated.resources.special
import falloutapp.composeapp.generated.resources.strength
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterCreationScreen(
    uiState: CharacterCreationUiState,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
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

        Button(
            onClick = { onComplete(text) }
        ) {
            Text("Done")
        }
    }
}

@Composable
fun SpecialAllocation(label: String, value: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Column {
        Text(label)
        Text("$value")
        Row {
            Button(onClick = onIncrement) {
                Text("+")
            }
            Button(onClick = onDecrement) {
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
            {}
        ) {  }
    }
}