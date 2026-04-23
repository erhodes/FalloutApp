package com.erhodes.falloutapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.model.action.Attack
import com.erhodes.falloutapp.model.action.RangedAttack
import com.erhodes.falloutapp.presentation.EnemyUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EnemyCharacterDisplay(
    state: EnemyUiState,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Stats.entries.forEach {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.border(1.dp, Color.Black)
                        .width(20.dp)
                ) {
                    Text(
                        text = stringResource(it.letter),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${state.character.getStatByOrdinal(it.ordinal)}",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        //Skills
        FlowRow(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0..11) {
                Text("${stringResource(Skills.entries[i].description)}: ${state.character.skills[i]}")
            }
        }
        //Actions
        if (state.character.actions.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
            ) {
                state.character.actions.forEach { action ->
                    Text("${action.title}: ${actionDescription(action, action.testValue(state.character))}")
                }
            }
        }
    }
}

@Preview
@Composable
fun EnemyCharacterDisplayPreview() {
    FalloutAppTheme {
        val weapon = Weapon(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ASSAULT_RIFLE), 1)
        val character = Character("Bob")
        character.actions.add(RangedAttack(weapon))
        EnemyCharacterDisplay(
            state = EnemyUiState(
                index = 0,
                character = character
            ),
        )
    }
}
