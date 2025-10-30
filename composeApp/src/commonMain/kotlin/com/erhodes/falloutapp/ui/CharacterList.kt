package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.Greeting
import com.erhodes.falloutapp.model.Character

@Composable
fun CharacterList(characters: List<Character>, onSelect: (Character) -> Unit, onNewCharacter: () -> Unit) {
    Column() {
        val greeting = Greeting().greet()
        Text(
            "${greeting}"
        )
        Text(
            "Please select a character"
        )
        characters.forEach {
            CharacterSelector(it) { onSelect(it) }
        }
        Button(
            onClick = onNewCharacter
        ) {
            Text("New")
        }
    }
}

@Composable
fun CharacterSelector(character: Character, onSelect: () -> Unit) {
    Row() {
        Text(
            text = character.name
        )
        Button(
            onClick = onSelect
        ) {
            Text("Select")
        }
    }
}