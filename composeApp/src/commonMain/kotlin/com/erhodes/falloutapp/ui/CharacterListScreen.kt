package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.Greeting
import com.erhodes.falloutapp.model.Character
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.delete
import org.jetbrains.compose.resources.stringResource

@Composable
fun CharacterListScreen(characters: List<Character>, onSelect: (Character) -> Unit, onNewCharacter: () -> Unit, onDeleteClicked: (Character) -> Unit) {
    Column() {
        val greeting = Greeting().greet()
        Text(
            greeting
        )
        Text(
            "Please select a character"
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
fun CharacterSelector(character: Character, onSelect: () -> Unit, onDeleteClicked: () -> Unit) {
    Row() {
        Text(
            text = character.name
        )
        Button(
            onClick = onSelect
        ) {
            Text("Select")
        }
        Button(
            onClick = onDeleteClicked,
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(stringResource(Res.string.delete))
        }
    }
}