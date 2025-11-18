package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.ItemTemplate
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.acquire
import falloutapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AcquireItemScreen(items: Collection<ItemTemplate>, onAcquireItem: (ItemTemplate) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var text by remember { mutableStateOf("") }

        val filtered = items.filter { item ->
            item.name.startsWith(text)
        }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(stringResource(Res.string.search)) }
        )

        filtered.forEach { template ->
            ItemTemplateDisplay(
                template = template,
                buttonLabel = stringResource(Res.string.acquire),
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