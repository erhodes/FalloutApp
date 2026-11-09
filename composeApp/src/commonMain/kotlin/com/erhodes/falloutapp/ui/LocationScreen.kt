package com.erhodes.falloutapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.presentation.BasicFeatureUiState
import com.erhodes.falloutapp.presentation.BasicLocationUiState
import com.erhodes.falloutapp.presentation.FeatureType
import com.erhodes.falloutapp.presentation.FeatureUiState
import com.erhodes.falloutapp.presentation.LocationUiState
import com.erhodes.falloutapp.presentation.SettlementUiState
import com.erhodes.falloutapp.presentation.StoreUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Editing happens entirely in the edit dialog, whose Save button commits the change through
 * [onEditLocation] - there is no separate save step. Features work the same way: the add dialog and
 * the per-feature delete button commit immediately through [onAddFeature] / [onRemoveFeature].
 *
 * @param readOnly when true the location is owned elsewhere (e.g. fetched from the server), so
 * every control that would mutate it is hidden and the location is only displayed.
 */
@Composable
fun LocationScreen(
    state: LocationUiState,
    onEditLocation: (name: String, description: String) -> Unit = { _, _ -> },
    onAddFeature: (type: FeatureType, name: String, description: String) -> Unit = { _, _, _ -> },
    onRemoveFeature: (id: String) -> Unit = {},
    onOpenStore: (id: String) -> Unit = {},
    readOnly: Boolean = false,
) {
    Column {
        LocationHeader(
            state = state,
            onEditLocation = onEditLocation,
            readOnly = readOnly
        )
        FeatureSection(
            features = state.features,
            onAddFeature = onAddFeature,
            onRemoveFeature = onRemoveFeature,
            onOpenStore = onOpenStore,
            readOnly = readOnly
        )
    }
}

@Composable
fun SettlementScreen(
    state: SettlementUiState,
    onEditLocation: (name: String, description: String) -> Unit = { _, _ -> },
    onAddFeature: (type: FeatureType, name: String, description: String) -> Unit = { _, _, _ -> },
    onRemoveFeature: (id: String) -> Unit = {},
    onOpenStore: (id: String) -> Unit = {},
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
        FeatureSection(
            features = state.features,
            onAddFeature = onAddFeature,
            onRemoveFeature = onRemoveFeature,
            onOpenStore = onOpenStore,
            readOnly = readOnly
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

/**
 * The location's features, shared by every location screen since every [LocationUiState] has them.
 */
@Composable
private fun FeatureSection(
    features: List<FeatureUiState>,
    onAddFeature: (type: FeatureType, name: String, description: String) -> Unit,
    onRemoveFeature: (id: String) -> Unit,
    onOpenStore: (id: String) -> Unit,
    readOnly: Boolean,
) {
    var showAddDialog by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Features",
            style = MaterialTheme.typography.titleLarge
        )
        if (!readOnly) {
            IconButton(onClick = { showAddDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add feature"
                )
            }
        }
    }
    if (features.isEmpty()) {
        Text(
            text = "No features.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)) {
        features.forEach { feature ->
            FeatureRow(
                feature = feature,
                onRemoveFeature = onRemoveFeature,
                onOpenStore = onOpenStore,
                readOnly = readOnly
            )
        }
    }
    if (showAddDialog) {
        AddFeatureDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { type, name, description ->
                onAddFeature(type, name, description)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun FeatureRow(
    feature: FeatureUiState,
    onRemoveFeature: (id: String) -> Unit,
    onOpenStore: (id: String) -> Unit,
    readOnly: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .padding(horizontal = Dimens.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = Dimens.paddingSmall)
        ) {
            Text(
                text = feature.name,
                style = MaterialTheme.typography.titleMedium
            )
            if (feature.description.isNotBlank()) {
                Text(
                    text = feature.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Text(
            text = feature.type.label,
            style = MaterialTheme.typography.labelMedium
        )
        // Only a store has a screen of its own so far, and only an owner can act on it - a read-only
        // location is just being displayed, so there is nothing to open into.
        if (feature.type == FeatureType.STORE && !readOnly) {
            TextButton(onClick = { onOpenStore(feature.id) }) {
                Text("Open")
            }
        }
        if (!readOnly) {
            IconButton(onClick = { onRemoveFeature(feature.id) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove ${feature.name}"
                )
            }
        }
    }
}

/** Picks the subclass to create along with its name and description. */
@Composable
private fun AddFeatureDialog(
    onDismiss: () -> Unit,
    onConfirm: (type: FeatureType, name: String, description: String) -> Unit,
) {
    var selectedType by remember { mutableStateOf(FeatureType.BASIC) }
    var newName by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Feature") },
        text = {
            Column {
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)) {
                    FeatureType.entries.forEach { type ->
                        FilterChip(
                            selected = type == selectedType,
                            onClick = { selectedType = type },
                            label = { Text(type.label) }
                        )
                    }
                }
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
            TextButton(
                onClick = { onConfirm(selectedType, newName, newDescription) },
                // A nameless feature would render as a blank row, so require one.
                enabled = newName.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
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
                food = 1,
                features = listOf(
                    BasicFeatureUiState(
                        id = "preview-well",
                        name = "Water Well",
                        description = "Hand-cranked, brackish."
                    ),
                    StoreUiState(
                        id = "preview-post",
                        name = "Trading Post",
                        description = "Buys scrap, sells ammo."
                    )
                )
            )
        )
    }
}
