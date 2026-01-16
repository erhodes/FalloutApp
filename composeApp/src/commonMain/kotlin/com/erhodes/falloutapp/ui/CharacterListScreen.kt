package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.erhodes.falloutapp.Greeting
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.delete
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterListScreen(characters: List<Character>, onSelect: (Character) -> Unit, onNewCharacter: () -> Unit, onDeleteClicked: (Character) -> Unit, onLogin: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Button(
            onClick = onLogin
        ) {
            Text("Login")
        }

        val greeting = Greeting().greet()
        Text(
            greeting
        )
        Text(
            text = "Please select a character",
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
fun CharacterSelector(character: Character, onSelect: () -> Unit, onDeleteClicked: () -> Unit, modifier: Modifier = Modifier) {
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
            onClick = onDeleteClicked,
            colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.errorContainer),
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(stringResource(Res.string.delete))
        }
    }
}

@Preview
@Composable
fun CharacterListScreenPreview() {
    FalloutAppTheme {
        val character1 = Character("Tom")
        val character2 = Character("Jerry")
        CharacterListScreen(
            characters = listOf(character1, character2),
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
            character = Character("Tom"),
            {},
            {}
        )
    }
}
