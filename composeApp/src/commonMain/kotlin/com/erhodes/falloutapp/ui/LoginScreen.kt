package com.erhodes.falloutapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * @param campaignName name of the fetched campaign, or null if campaign data hasn't been fetched.
 * @param campaignJoined whether Join Campaign has succeeded (persisted across restarts). The
 * Register section starts collapsed on each visit while this is true.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loggedIn: Boolean,
    characters: List<PlayerCharacter>,
    initialName: String,
    initialAddress: String,
    connectExpanded: Boolean,
    onConnectExpandedChange: (Boolean) -> Unit,
    campaignName: String?,
    campaignJoined: Boolean,
    onLogin: (String, String) -> Unit,
    onJoinCampaign: (PlayerCharacter) -> Unit,
    onActiveEncounterClicked: () -> Unit,
    onActiveLocationClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember(initialName) { mutableStateOf(initialName) }
    var address by remember(initialAddress) {
        mutableStateOf(initialAddress.ifEmpty { "10.0.0.166" })
    }
    var selectedCharacter by remember(characters) { mutableStateOf(characters.firstOrNull()) }
    var characterMenuExpanded by remember { mutableStateOf(false) }
    val hasCharacters = characters.isNotEmpty()

    // Each visit to this screen is a new back-stack entry, so these reset on every entry:
    // once a campaign has been joined, Register starts closed and Campaign starts open.
    var registerExpanded by rememberSaveable { mutableStateOf(!campaignJoined) }
    var campaignExpanded by rememberSaveable { mutableStateOf(campaignJoined) }
    var wasJoined by rememberSaveable { mutableStateOf(campaignJoined) }
    LaunchedEffect(campaignJoined) {
        // A join that succeeds while the screen is open swaps which section is showing.
        if (campaignJoined && !wasJoined) {
            registerExpanded = false
            campaignExpanded = true
        }
        wasJoined = campaignJoined
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = Dimens.paddingSmall)
    ) {
        SectionHeader(
            title = "Connect",
            status = if (loggedIn) "Connected" else null,
            expanded = connectExpanded,
            onToggle = { onConnectExpandedChange(!connectExpanded) }
        )
        AnimatedVisibility(visible = connectExpanded) {
            Column {
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
                Button(onClick = { onLogin(name, address) }) {
                    Text("Login")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        SectionHeader(
            title = "Register",
            status = if (campaignJoined) "Joined" else null,
            expanded = registerExpanded,
            onToggle = { registerExpanded = !registerExpanded }
        )
        AnimatedVisibility(visible = registerExpanded) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                if (campaignName == null) {
                    Text("No campaign data yet. Connect to a server to load the campaign.")
                } else {
                    Text(
                        text = campaignName,
                        style = MaterialTheme.typography.titleSmall
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
                    Button(
                        onClick = { selectedCharacter?.let(onJoinCampaign) },
                        enabled = selectedCharacter != null
                    ) {
                        Text("Join Campaign")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        SectionHeader(
            title = "Campaign",
            status = null,
            expanded = campaignExpanded,
            onToggle = { campaignExpanded = !campaignExpanded }
        )
        AnimatedVisibility(visible = campaignExpanded) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                when {
                    !campaignJoined -> Text("You need to join a campaign first.")
                    campaignName == null -> Text("Campaign data hasn't loaded yet. Connect to the server to load it.")
                    else -> CampaignContent(
                        campaignName = campaignName,
                        onActiveEncounterClicked = onActiveEncounterClicked,
                        onActiveLocationClicked = onActiveLocationClicked
                    )
                }
            }
        }
    }
}

/** Campaign name plus the Active Encounter / Active Location buttons. */
@Composable
private fun CampaignContent(campaignName: String, onActiveEncounterClicked: () -> Unit, onActiveLocationClicked: () -> Unit) {
    Column {
        Text(
            text = campaignName,
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            onClick = onActiveEncounterClicked
        ) {
            Text("Active Encounter")
        }
        Button(
            onClick = onActiveLocationClicked
        ) {
            Text("Active Location")
        }
    }
}

/** Clickable title bar for a collapsible section, with an optional status label. */
@Composable
private fun SectionHeader(
    title: String,
    status: String?,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceDim)
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(horizontal = Dimens.paddingMedium, vertical = Dimens.paddingSmall)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.weight(1f))
        if (status != null) {
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = if (expanded) "Collapse" else "Expand"
        )
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
            connectExpanded = true,
            onConnectExpandedChange = {},
            campaignName = "Test Campaign",
            campaignJoined = false,
            onLogin = { a, b -> },
            onJoinCampaign = {},
            onActiveEncounterClicked = {},
            onActiveLocationClicked = {}
        )
    }
}
