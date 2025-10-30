package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
fun CharacterCreation(onComplete: (String) -> Unit) {
    Column {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Name") }
        )

        Button(
            onClick = { onComplete(text) }
        ) {
            Text("Done")
        }
    }
}