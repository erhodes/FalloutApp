package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loggedIn: Boolean,
    characters: List<PlayerCharacter>,
    initialName: String,
    initialAddress: String,
    onLogin: (String, String) -> Unit,
    onJoinCampaign: (PlayerCharacter) -> Unit,
    onGetEncounter: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember(initialName) { mutableStateOf(initialName) }
    var address by remember(initialAddress) {
        mutableStateOf(initialAddress.ifEmpty { "10.0.0.166" })
    }
    var selectedCharacter by remember(characters) { mutableStateOf(characters.firstOrNull()) }
    var characterMenuExpanded by remember { mutableStateOf(false) }
    val hasCharacters = characters.isNotEmpty()

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = Dimens.paddingSmall)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium
        )
        if (loggedIn) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Logged In successfully!")
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Login name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Server Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = characterMenuExpanded && hasCharacters,
            onExpandedChange = { if (hasCharacters) characterMenuExpanded = !characterMenuExpanded }
        ) {
            OutlinedTextField(
                value = if (hasCharacters) selectedCharacter?.name.orEmpty() else "No characters available",
                onValueChange = {},
                readOnly = true,
                enabled = hasCharacters,
                label = { Text("Character") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = characterMenuExpanded && hasCharacters)
                },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = characterMenuExpanded && hasCharacters,
                onDismissRequest = { characterMenuExpanded = false }
            ) {
                characters.forEach { character ->
                    DropdownMenuItem(
                        text = { Text(character.name) },
                        onClick = {
                            selectedCharacter = character
                            characterMenuExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onLogin(name, address) }) {
            Text("Login")
        }
        Button(
            onClick = { selectedCharacter?.let(onJoinCampaign) },
            enabled = selectedCharacter != null
        ) {
            Text("Join Campaign")
        }
        Button(
            onClick = { onGetEncounter() }
        ) {
            Text("Get Encounter")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    FalloutAppTheme {
        LoginScreen(
            loggedIn = true,
            characters = emptyList(),
            initialName = "",
            initialAddress = "",
            onLogin = { a, b -> },
            onJoinCampaign = {},
            onGetEncounter = {}
        )
    }
}
