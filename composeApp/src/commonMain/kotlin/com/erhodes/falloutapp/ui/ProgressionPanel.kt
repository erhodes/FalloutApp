package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.gain_milestone
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProgressionPanel(state: CharacterUiState, onGainMilestone: () -> Unit, modifier: Modifier = Modifier) {
    val character = state.character
    Column {
        Header(text = "Progression")
        Column(
            modifier = modifier.padding(horizontal = Dimens.paddingMedium)
        ) {
            Text(
                text = "Level ${character.level}",
                style = MaterialTheme.typography.titleLarge
            )
            Text("Milestones ${character.milestones}/3   Max Perks:${character.level}  Max Skill:${character.getMaxSkillValue()}")
            if (state.editable) {
                Button(
                    onClick = onGainMilestone
                ) {
                    Text(stringResource(Res.string.gain_milestone))
                }
            }
        }
    }

}

@Preview
@Composable
fun ProgressionPanelPreview() {
    FalloutAppTheme {
        ProgressionPanel(
            state = CharacterUiState(Character("Tom")),
            onGainMilestone = {}
        )
    }
}