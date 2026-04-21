package com.erhodes.falloutapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.presentation.EncounterUiState
import com.erhodes.falloutapp.presentation.EnemyUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EncounterScreen(
    state: EncounterUiState,
    onAddEnemyClicked: () -> Unit,
    onTakeDamage: (enemyIndex: Int, amount: Int) -> Unit,
    onHealDamage: (enemyIndex: Int, amount: Int) -> Unit,
    onRepair: (enemyIndex: Int, amount: Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = Dimens.paddingMedium)
    ) {
        Text(
            text = state.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Characters:",
            style = MaterialTheme.typography.headlineSmall
        )
        val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }
        state.enemies.forEach { enemy ->
            Text(
                text = enemy.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .clickable {
                        expandedStates[enemy.index] = !(expandedStates[enemy.index] ?: false)
                    }
            )
            if (expandedStates[enemy.index] == true) {
                EnemyCharacterDisplay(
                    state = enemy,
                    onTakeDamage = { onTakeDamage(enemy.index, it) },
                    onHealDamage = { onHealDamage(enemy.index, it) },
                    onRepair = { onRepair(enemy.index, it) },
                )
            }
        }
        Button(
            onClick = onAddEnemyClicked
        ) {
            Text("Add Enemy")
        }
    }
}

@Preview
@Composable
fun EncounterScreenPreview() {
    FalloutAppTheme {
        EncounterScreen(
            state = EncounterUiState(
                name = "Test",
                enemies = listOf(
                    EnemyUiState(0, "Bob", List(7) { 1 }, List(12) { 2 }, 0, false, 0, 0, 0),
                    EnemyUiState(1, "Deathclaw", List(7) { 1 }, List(12) { 2 }, 0, false, 0, 0, 0),
                ),
            ),
            onAddEnemyClicked = {},
            onTakeDamage = { _, _ -> },
            onHealDamage = { _, _ -> },
            onRepair = { _, _ -> },
        )
    }
}
