package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.backpack_24dp
import falloutapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AcquireItemScreen(
    items: Collection<ItemTemplate>,
    selectedTier: Int,
    onTierChanged: (Int) -> Unit,
    onAcquireItem: (ItemTemplate) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val filtered = items.filter { item ->
            item.name.lowercase().contains(text.lowercase())
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(Res.string.search)) },
                modifier = Modifier.weight(1f)
            )

            Box {
                TextButton(onClick = { expanded = true }) {
                    Text("Tier: ${if (selectedTier == 0) "All" else selectedTier}")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf(0, 1, 2).forEach { tier ->
                        DropdownMenuItem(
                            text = { Text(if (tier == 0) "All" else tier.toString()) },
                            onClick = {
                                onTierChanged(tier)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        filtered.forEach { template ->
            ItemTemplateDisplay(
                template = template,
                buttonIcon = Res.drawable.backpack_24dp,
                buttonAction = { onAcquireItem(template) }
            )
        }
    }
}

@Composable
@Preview
fun AcquireItemScreenPreview() {
    val itemTemplates = arrayListOf(
        ItemDataSource.getItemTemplateById(ItemDataSource.ID_BATTLE_STANDARD),
        ItemDataSource.getItemTemplateById(ItemDataSource.ID_ARMOR_LEATHER)
    )
    FalloutAppTheme {
        AcquireItemScreen(itemTemplates, 0, {}, {})
    }
}