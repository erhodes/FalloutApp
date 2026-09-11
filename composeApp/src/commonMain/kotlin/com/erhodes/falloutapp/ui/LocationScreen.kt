package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.erhodes.falloutapp.presentation.BasicLocationUiState
import com.erhodes.falloutapp.presentation.LocationUiState
import com.erhodes.falloutapp.presentation.SettlementUiState
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Editing happens entirely in the edit dialog, whose Save button commits the change through
 * [onEditLocation] — there is no separate save step.
 *
 * @param readOnly when true the location is owned elsewhere (e.g. fetched from the server), so
 * every control that would mutate it is hidden and the location is only displayed.
 */
@Composable
fun LocationScreen(
    state: LocationUiState,
    onEditLocation: (name: String, description: String) -> Unit = { _, _ -> },
    readOnly: Boolean = false,
) {
    Column {
        LocationHeader(
            state = state,
            onEditLocation = onEditLocation,
            readOnly = readOnly
        )
    }
}

@Composable
fun SettlementScreen(
    state: SettlementUiState,
    onEditLocation: (name: String, description: String) -> Unit = { _, _ -> },
    readOnly: Boolean = false,
) {
    Column {
        LocationHeader(
            state = state,
            onEditLocation = onEditLocation,
            readOnly = readOnly
        )
        Text(
            text = "Population ${state.population}"
        )
        Text(
            text = "Food ${state.food}"
        )
    }
}

/** Name and description, plus the edit control that changes them. */
@Composable
private fun LocationHeader(
    state: LocationUiState,
    onEditLocation: (name: String, description: String) -> Unit,
    readOnly: Boolean,
) {
    var showEditDialog by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = state.name,
            style = MaterialTheme.typography.displaySmall
        )
        if (!readOnly) {
            IconButton(onClick = { showEditDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit location"
                )
            }
        }
    }
    if (state.description.isNotBlank()) {
        Text(
            text = state.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    if (showEditDialog) {
        var newName by remember { mutableStateOf(state.name) }
        var newDescription by remember { mutableStateOf(state.description) }
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Location") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Name") }
                    )
                    OutlinedTextField(
                        value = newDescription,
                        onValueChange = { newDescription = it },
                        label = { Text("Description") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onEditLocation(newName, newDescription)
                    showEditDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview
@Composable
fun LocationScreenPreview() {
    FalloutAppTheme {
        LocationScreen(
            state = BasicLocationUiState(
                id = "preview-wastes",
                name = "The Wastes",
                description = "Irradiated scrubland stretching to the horizon."
            )
        )
    }
}

@Preview
@Composable
fun SettlementScreenPreview() {
    FalloutAppTheme {
        SettlementScreen(
            state = SettlementUiState(
                id = "preview-honeywell",
                name = "Honeywell",
                description = "A walled farming town built around an old water plant.",
                population = 1,
                food = 1
            )
        )
    }
}
