package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.ItemTemplate
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AcquireItemScreen(items: Collection<ItemTemplate>, onAcquireItem: (ItemTemplate) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        items.forEach { template ->
            ItemTemplateDisplay(
                template = template,
                buttonLabel = "Acquire",
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
    MaterialTheme {
        AcquireItemScreen(itemTemplates, {})
    }
}