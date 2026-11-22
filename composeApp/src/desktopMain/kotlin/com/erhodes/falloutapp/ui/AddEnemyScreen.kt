package com.erhodes.falloutapp.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.data.EnemyDataSource
import com.erhodes.falloutapp.model.EnemyTemplate
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme

@Composable
fun AddEnemyScreen(onEnemySelected: (EnemyTemplate) -> Unit) {
    Column {
        EnemyDataSource.all.forEach {
            Row {
                Text(it.name)
                Button(
                    onClick = { onEnemySelected(it) }
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Preview
@Composable
fun AddEnemyScreenPreview() {
    FalloutAppTheme {
        AddEnemyScreen(
            onEnemySelected = {}
        )
    }
}
