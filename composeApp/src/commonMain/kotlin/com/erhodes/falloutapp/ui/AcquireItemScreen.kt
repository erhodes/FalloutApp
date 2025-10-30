package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.repository.ItemRepository
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AcquireItemScreen(items: List<ItemTemplate>, onAcquireItem: (ItemTemplate) -> Unit) {
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
    val items = arrayListOf(
        ItemRepository.BANNER,
        ItemRepository.LEATHER_ARMOR
    )
    MaterialTheme {
        AcquireItemScreen(items, {})
    }
}