package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.Greeting
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.delete
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterListScreen(
    characters: List<PlayerCharacter>,
    connected: Boolean,
    onSelect: (PlayerCharacter) -> Unit,
    onNewCharacter: () -> Unit,
    onDeleteClicked: (PlayerCharacter) -> Unit,
    onLogin: () -> Unit,
    onCampaignClicked: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Row {
            Button(
                onClick = onLogin
            ) {
                Text("Login")
            }
            Text(
                text = if (connected) "Connected" else "Not Connected"
            )
        }
        Button(
            onClick = onCampaignClicked
        ) {
            Text("Campaign")
        }

        val greeting = Greeting().greet()
        Text(
            greeting
        )
        Text(
            text = "Your Characters",
            style = MaterialTheme.typography.displaySmall
        )
        characters.forEach {
            CharacterSelector(
                character = it,
                onSelect = { onSelect(it) },
                onDeleteClicked = { onDeleteClicked(it) }
            )
        }
        Button(
            onClick = onNewCharacter
        ) {
            Text("New")
        }
    }
}

@Composable
fun CharacterSelector(character: PlayerCharacter, onSelect: () -> Unit, onDeleteClicked: () -> Unit, modifier: Modifier = Modifier) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Delete ${character.name}?") },
            text = { Text("This character will be permanently deleted. This cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirmation = false
                        onDeleteClicked()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(Res.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Row(
        modifier = modifier
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(0.2f)
        )
        Button(
            onClick = onSelect
        ) {
            Text("Select")
        }
        Button(
            onClick = { showDeleteConfirmation = true },
            colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.errorContainer),
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(stringResource(Res.string.delete))
        }
    }
}

@Composable
fun RemoteCharacterSelector(character: PlayerCharacter, onSelect: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(0.2f)
        )
        Button(
            onClick = onSelect
        ) {
            Text("View")
        }
    }
}

@Preview
@Composable
fun CharacterListScreenPreview() {
    FalloutAppTheme {
        val character1 = PlayerCharacter("Tom")
        val character2 = PlayerCharacter("Jerry")
        CharacterListScreen(
            characters = listOf(character1, character2),
            connected = false,
            {},
            {},
            {},
            {},
            {}
        )
    }
}

@Preview
@Composable
fun CharacterSelectorPreview() {
    FalloutAppTheme {
        CharacterSelector(
            character = PlayerCharacter("Tom"),
            {},
            {}
        )
    }
}
