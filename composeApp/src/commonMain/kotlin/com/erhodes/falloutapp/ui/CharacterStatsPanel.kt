package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.skills
import org.jetbrains.compose.resources.stringResource

@Composable
fun CharacterStatsPanel(character: Character) {
    Column {
        // SPECIAL
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Stats.entries.forEach {
                SpecialPanel(
                    title = stringResource(it.letter),
                    value = character.getStatByOrdinal(it.ordinal),
                    modifier = Modifier.weight(1f).padding(horizontal = 2.dp)
                )
            }
        }

        //Skills
        Header(stringResource(Res.string.skills))
        Row(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
        ) {
            Column(
                modifier = Modifier.padding(end = 10.dp)
            ) {
                for (i in 0.. 5) {
                    Text("${stringResource(Skills.entries[i].description)}: ${character.skills[i]}")
                }
            }
            Column {
                for (i in 6.. 11) {
                    Text("${stringResource(Skills.entries[i].description)}: ${character.skills[i]}")
                }
            }
        }
    }
}

@Composable
fun SpecialPanel(title: String, value: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(4.dp),
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$value",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}