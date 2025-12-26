package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.presentation.GainSkillUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.done
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GainSkillsScreen(uiState: GainSkillUiState, onIncreaseClicked: (Int) -> Unit, onDecreaseClicked: (Int) -> Unit, onFinalizeClicked: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
    ) {
        Text("You may increase ${uiState.bonuses} more skills")
        for (i in 0 .. 11) {
            val skillLevel = uiState.character.skills[i] + uiState.appliedBonuses[i]
            ModifySkillPanel(
                ordinal = i,
                value = skillLevel,
                increaseEnabled = skillLevel < uiState.character.getMaxSkillValue() && uiState.bonuses > 0,
                decreaseEnabled = uiState.appliedBonuses[i] > 0,
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
fun ModifySkillPanel(ordinal: Int,
                     value: Int,
                     increaseEnabled: Boolean,
                     decreaseEnabled: Boolean,
                     onIncreaseClicked: (Int) -> Unit, onDecreaseClicked: (Int) -> Unit) {
    Row {
        Text("${stringResource(Skills.entries[ordinal].description)} $value")
        Button(
            enabled = increaseEnabled,
            onClick = { onIncreaseClicked(ordinal) }
        ) {
            Text("+")
        }
        Button(
            enabled = decreaseEnabled,
            onClick = { onDecreaseClicked(ordinal) }
        ) {
            Text("-")
        }
    }
}

@Preview
@Composable
fun GainSkillScreenPreview() {
    MaterialTheme {
        GainSkillsScreen(
            uiState = GainSkillUiState(character = Character("Tom"), 2, false),
            {},
            {},
            {}
        )
    }
}