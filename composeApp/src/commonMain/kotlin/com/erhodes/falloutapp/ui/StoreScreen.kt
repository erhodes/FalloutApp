package com.erhodes.falloutapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.presentation.BuyerUiState
import com.erhodes.falloutapp.presentation.StockEntryUiState
import com.erhodes.falloutapp.presentation.StoreUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/** How many search matches the add-stock dialog lists, so the whole catalog can't fill the dialog. */
private const val MAX_TEMPLATE_MATCHES = 40

/**
 * A single store's stock: what it sells, and the controls to buy from it or change what it carries.
 *
 * A purchase needs a buyer, so the screen picks one character to act as the buyer and every Buy
 * button sells to them. Both the buyer and a stock entry are addressed by index - see
 * [StockEntryUiState] for why - and every control commits immediately through its callback, the same
 * way [LocationScreen]'s feature controls do.
 *
 * The two permissions are separate because the two users of this screen differ: a GM can do both,
 * while a player can buy from a store they are visiting but not change what it carries. Both default
 * to false, so a caller that passes neither gets a display-only store.
 *
 * @param canBuy whether the Buy controls and the buyer picker are shown.
 * @param canEditStock whether the stock can be added to, re-priced or removed.
 */
@Composable
fun StoreScreen(
    state: StoreUiState,
    buyers: List<BuyerUiState> = emptyList(),
    templates: Collection<ItemTemplate> = emptyList(),
    message: String? = null,
    onDismissMessage: () -> Unit = {},
    onBuy: (entryIndex: Int, buyerIndex: Int) -> Unit = { _, _ -> },
    onAddStock: (templateId: Int, quantity: Int, price: Int) -> Unit = { _, _, _ -> },
    onEditStock: (entryIndex: Int, quantity: Int, price: Int) -> Unit = { _, _, _ -> },
    onRemoveStock: (entryIndex: Int) -> Unit = {},
    canBuy: Boolean = false,
    canEditStock: Boolean = false,
) {
    var selectedBuyer by remember { mutableStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingIndex by remember { mutableStateOf(-1) }

    // The roster can shrink under us, which would leave the selection pointing past the end.
    val buyerIndex = selectedBuyer.coerceIn(0, (buyers.size - 1).coerceAtLeast(0))
    val buyer = buyers.getOrNull(buyerIndex)

    Column(modifier = Modifier.padding(horizontal = Dimens.paddingMedium)) {
        Text(
            text = state.name,
            style = MaterialTheme.typography.displaySmall
        )
        if (state.description.isNotBlank()) {
            Text(
                text = state.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (canBuy) {
            BuyerPicker(
                buyers = buyers,
                selected = buyer,
                onSelect = { selectedBuyer = it }
            )
        }
        if (message != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDismissMessage) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Dismiss message"
                    )
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Stock",
                style = MaterialTheme.typography.titleLarge
            )
            if (canEditStock) {
                IconButton(onClick = { showAddDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add stock"
                    )
                }
            }
        }
        if (state.stock.isEmpty()) {
            Text(
                text = "Nothing in stock.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)) {
            state.stock.forEachIndexed { index, entry ->
                StockRow(
                    entry = entry,
                    buyer = buyer,
                    onBuy = { onBuy(index, buyerIndex) },
                    onEdit = { editingIndex = index },
                    onRemove = { onRemoveStock(index) },
                    canBuy = canBuy,
                    canEditStock = canEditStock
                )
            }
        }
    }
    if (showAddDialog) {
        AddStockDialog(
            templates = templates,
            onDismiss = { showAddDialog = false },
            onConfirm = { templateId, quantity, price ->
                onAddStock(templateId, quantity, price)
                showAddDialog = false
            }
        )
    }
    val editing = state.stock.getOrNull(editingIndex)
    if (editing != null) {
        EditStockDialog(
            entry = editing,
            onDismiss = { editingIndex = -1 },
            onConfirm = { quantity, price ->
                onEditStock(editingIndex, quantity, price)
                editingIndex = -1
            }
        )
    }
}

/**
 * Chooses which character the Buy buttons sell to, showing what each can currently afford.
 *
 * A player only ever has one character to buy with, so a single buyer is stated rather than offered
 * as a choice of one.
 */
@Composable
private fun BuyerPicker(
    buyers: List<BuyerUiState>,
    selected: BuyerUiState?,
    onSelect: (index: Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    if (buyers.isEmpty()) {
        Text(
            text = "No characters available to buy.",
            style = MaterialTheme.typography.bodyMedium
        )
        return
    }
    if (buyers.size == 1) {
        val only = buyers.first()
        Text(
            text = "Buying as ${only.name} - ${only.caps} caps",
            style = MaterialTheme.typography.bodyLarge
        )
        return
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Buying as",
            style = MaterialTheme.typography.bodyLarge
        )
        Box {
            TextButton(onClick = { expanded = true }) {
                Text(
                    text = selected?.let { "${it.name} - ${it.caps} caps" } ?: "Select character"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                buyers.forEachIndexed { index, candidate ->
                    DropdownMenuItem(
                        text = { Text("${candidate.name} - ${candidate.caps} caps") },
                        onClick = {
                            onSelect(index)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun StockRow(
    entry: StockEntryUiState,
    buyer: BuyerUiState?,
    onBuy: () -> Unit,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
    canBuy: Boolean,
    canEditStock: Boolean,
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
                text = entry.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${entry.price} caps - " +
                    if (entry.quantity > 0) "${entry.quantity} in stock" else "sold out",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (canBuy) {
            Button(
                onClick = onBuy,
                // Both conditions are re-checked by the model; disabling just avoids a pointless
                // round trip and shows the player up front that they can't afford it.
                enabled = entry.quantity > 0 && buyer != null && buyer.caps >= entry.price
            ) {
                Text("Buy")
            }
        }
        if (canEditStock) {
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit ${entry.name} stock"
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove ${entry.name} from stock"
                )
            }
        }
    }
}

/** Picks an item out of the catalog and sets the quantity and price the store carries it at. */
@Composable
private fun AddStockDialog(
    templates: Collection<ItemTemplate>,
    onDismiss: () -> Unit,
    onConfirm: (templateId: Int, quantity: Int, price: Int) -> Unit,
) {
    var search by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<ItemTemplate?>(null) }
    var quantity by remember { mutableStateOf("1") }
    var price by remember { mutableStateOf("") }

    val matches = templates
        .filter { it.name.contains(search, ignoreCase = true) }
        .sortedBy { it.name }
        .take(MAX_TEMPLATE_MATCHES)
    val template = selected
    val quantityValue = quantity.toIntOrNull()
    val priceValue = price.toIntOrNull()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Stock") },
        text = {
            Column {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = { Text("Search items") }
                )
                Column(
                    modifier = Modifier
                        .heightIn(max = 160.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (matches.isEmpty()) {
                        Text(
                            text = "No matching items.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    matches.forEach { candidate ->
                        val isSelected = candidate.id == template?.id
                        Text(
                            text = candidate.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else Color.Transparent
                                )
                                .clickable { selected = candidate }
                                .padding(Dimens.paddingSmall)
                        )
                    }
                }
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") }
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price in caps") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Guarded by `enabled`, but re-checked so the smart casts hold without `!!`.
                    if (template != null && quantityValue != null && priceValue != null) {
                        onConfirm(template.id, quantityValue, priceValue)
                    }
                },
                enabled = template != null &&
                    quantityValue != null && quantityValue >= 0 &&
                    priceValue != null && priceValue >= 0
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

/** Restocks or re-prices an entry the store already carries. */
@Composable
private fun EditStockDialog(
    entry: StockEntryUiState,
    onDismiss: () -> Unit,
    onConfirm: (quantity: Int, price: Int) -> Unit,
) {
    var quantity by remember { mutableStateOf(entry.quantity.toString()) }
    var price by remember { mutableStateOf(entry.price.toString()) }
    val quantityValue = quantity.toIntOrNull()
    val priceValue = price.toIntOrNull()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(entry.name) },
        text = {
            Column {
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") }
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price in caps") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (quantityValue != null && priceValue != null) {
                        onConfirm(quantityValue, priceValue)
                    }
                },
                enabled = quantityValue != null && quantityValue >= 0 &&
                    priceValue != null && priceValue >= 0
            ) {
                Text("Save")
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
fun StoreScreenPreview() {
    FalloutAppTheme {
        StoreScreen(
            state = StoreUiState(
                id = "preview-post",
                name = "Mo's Trading Post",
                description = "Buys scrap, sells ammo.",
                stock = listOf(
                    StockEntryUiState(
                        templateId = ItemDataSource.ID_ASSAULT_RIFLE,
                        name = "Assault Rifle",
                        quantity = 2,
                        price = 120
                    ),
                    StockEntryUiState(
                        templateId = ItemDataSource.ID_ARMOR_LEATHER,
                        name = "Leather Armor",
                        quantity = 0,
                        price = 45
                    )
                )
            ),
            buyers = listOf(
                BuyerUiState(name = "Bob", caps = 130),
                BuyerUiState(name = "Nadine", caps = 12)
            ),
            canBuy = true,
            canEditStock = true
        )
    }
}
