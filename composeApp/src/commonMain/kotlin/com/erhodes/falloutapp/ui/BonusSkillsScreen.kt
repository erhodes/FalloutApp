package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.presentation.BonusSkillUiState
import com.erhodes.falloutapp.util.AppLogger
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.done
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BonusSkillsScreen(uiState: BonusSkillUiState, onIncreaseClicked: (Int) -> Unit, onDecreaseClicked: (Int) -> Unit, onFinalizeClicked: () -> Unit) {
    Column {
        Text("You may increase ${uiState.bonuses} more skills")
        for (i in 0 .. 11) {
            BonusSkillPanel(
                i,
                uiState.character.skills[i] + uiState.appliedBonuses[i],
                onIncreaseClicked = onIncreaseClicked,
                onDecreaseClicked = onDecreaseClicked
            )
        }
        Button(
            onClick = onFinalizeClicked
        ) {
            Text(stringResource(Res.string.done))
        }
    }
}

@Composable
fun BonusSkillPanel(ordinal: Int, value: Int, onIncreaseClicked: (Int) -> Unit, onDecreaseClicked: (Int) -> Unit) {
    Row {
        Text("${stringResource(Skills.entries[ordinal].description)} $value")
        Button(
            onClick = { onIncreaseClicked(ordinal) }
        ) {
            Text("+")
        }
        Button(
            onClick = { onDecreaseClicked(ordinal) }
        ) {
            Text("-")
        }
    }
}

@Preview
@Composable
fun BonusSkillScreenPreview() {
    MaterialTheme {
        BonusSkillsScreen(
            uiState = BonusSkillUiState(character = Character("Tom"), 2),
            {},
            {},
            {}
        )
    }
}