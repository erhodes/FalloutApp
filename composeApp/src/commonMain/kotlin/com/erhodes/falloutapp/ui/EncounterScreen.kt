package com.erhodes.falloutapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Enemy
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
    onRemoveEnemy: (enemyIndex: Int) -> Unit,
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
            EnemyRow(
                enemyUiState = enemy,
                expanded = expandedStates[enemy.index] ?: false,
                onToggleExpanded = {
                    expandedStates[enemy.index] = !(expandedStates[enemy.index] ?: false)
                },
                onTakeDamage = { onTakeDamage(enemy.index, it) },
                onHealDamage = { onHealDamage(enemy.index, it) },
                onRepair = { onRepair(enemy.index, it) },
                onRemove = { onRemoveEnemy(enemy.index) },
            )
        }
        Button(
            onClick = onAddEnemyClicked
        ) {
            Text("Add Enemy")
        }
    }
}

@Composable
private fun EnemyRow(
    enemyUiState: EnemyUiState,
    expanded: Boolean,
    onToggleExpanded: () -> Unit,
    onTakeDamage: (Int) -> Unit,
    onHealDamage: (Int) -> Unit,
    onRepair: (Int) -> Unit,
    onRemove: () -> Unit,
) {
    val enemy = enemyUiState.character
    var amount by remember { mutableStateOf("1") }
    val actionButtonPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(top = 5.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .clickable{ onToggleExpanded() }
    ) {
        Text(
            text = enemy.name,
            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.clickable { onToggleExpanded() }
        )
        Text(
            text = "${enemy.damageTaken}/${Character.MAX_HEALTH}",
            color = if (enemy.isBloodied()) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
        Text("Armor(${enemy.getArmorToughness()}) ${enemy.getArmorDamage()}/${enemy.getArmorDurability()}")
        Spacer(Modifier.width(4.dp))
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(64.dp)
        )
        Button(
            onClick = {
                onTakeDamage(amount.toIntOrNull() ?: 0)
                amount = "1"
            },
            contentPadding = actionButtonPadding,
        ) {
            Text("D")
        }
        Button(
            onClick = {
                onHealDamage(amount.toIntOrNull() ?: 0)
                amount = "1"
            },
            contentPadding = actionButtonPadding,
        ) {
            Text("H")
        }
        Button(
            onClick = {
                onRepair(amount.toIntOrNull() ?: 0)
                amount = "1"
            },
            contentPadding = actionButtonPadding,
        ) {
            Text("R")
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Remove ${enemy.name}"
            )
        }
    }
    if (expanded) {
        EnemyCharacterDisplay(state = enemyUiState)
    }
}

@Preview
@Composable
fun EncounterScreenPreview() {
    FalloutAppTheme {
        val bob = Enemy("Bob")
        val deathclaw = Enemy("Deathclaw")
        EncounterScreen(
            state = EncounterUiState(
                name = "Test",
                enemies = listOf(
                    EnemyUiState(0, bob),
                    EnemyUiState(1, deathclaw),
                ),
            ),
            onAddEnemyClicked = {},
            onTakeDamage = { _, _ -> },
            onHealDamage = { _, _ -> },
            onRepair = { _, _ -> },
            onRemoveEnemy = {},
        )
    }
}
