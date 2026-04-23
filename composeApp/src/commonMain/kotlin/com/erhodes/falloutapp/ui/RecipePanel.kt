package com.erhodes.falloutapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.Recipe
import com.erhodes.falloutapp.ui.theme.Dimens
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecipePanel(
    recipe: Recipe,
    buttonLabel: String? = null,
    buttonEnabled: Boolean = false,
    onClick: () -> Unit = {},
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val costItem = ItemDataSource.getItemTemplateById(recipe.type.costItemId)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .padding(horizontal = Dimens.paddingMedium, vertical = Dimens.paddingSmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = recipe.itemTemplate.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(1f))
            if (buttonLabel != null) {
                Button(
                    enabled = buttonEnabled,
                    onClick = onClick
                ) {
                    Text(buttonLabel)
                }
            }
            if (onDelete != null) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Remove ${recipe.itemTemplate.name}"
                    )
                }
            }
        }
        Text(
            text = "${stringResource(recipe.type.skill.description)} ${recipe.complexity} • ${recipe.cost} ${costItem.name}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
