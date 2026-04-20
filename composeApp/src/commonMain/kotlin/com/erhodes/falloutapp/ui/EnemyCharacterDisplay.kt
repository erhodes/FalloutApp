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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.heal
import falloutapp.composeapp.generated.resources.repair
import falloutapp.composeapp.generated.resources.take_damage
import falloutapp.composeapp.generated.resources.toughness
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EnemyCharacterDisplay(character: Character, onTakeDamage:(Int) -> Unit, onHealDamage:(Int) -> Unit, onRepair:(Int) -> Unit) {

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
                        text = "${character.getStatByOrdinal(it.ordinal)}",
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
            for (i in 0.. 11) {
                Text("${stringResource(Skills.entries[i].description)}: ${character.skills[i]}")
            }
        }
        //Vitals Condensed
        Row {
            Text(
                text = "Damage Taken ${character.damageTaken}/${Character.MAX_HEALTH}",
                color = if (character.isBloodied()) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            Text(". Armor(${character.getArmorToughness()}) ${character.getArmorDamage()}/${character.getArmorDurability()}")
        }
        var amount by remember { mutableStateOf("") }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = {
                val current = amount.toIntOrNull()
                amount = if (current == null) "1" else (current + 1).toString()
            }) {
                Text("+")
            }
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Amount") },
                modifier = Modifier.width(100.dp)
            )
            Button(onClick = {
                val current = amount.toIntOrNull()
                amount = if (current == null) "1" else (current - 1).toString()
            }) {
                Text("-")
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    onTakeDamage(amount.toIntOrNull() ?: 0)
                    amount = ""
                }
            ) {
                Text(stringResource(Res.string.take_damage))
            }
            Button(
                onClick = {
                    onHealDamage(amount.toIntOrNull() ?: 0)
                    amount = ""
                }
            ) {
                Text(stringResource(Res.string.heal))
            }
            Button(
                onClick = {
                    onRepair(amount.toIntOrNull() ?: 0)
                    amount = ""
                }
            ) {
                Text(stringResource(Res.string.repair))
            }
        }
    }
}

@Preview
@Composable
fun EnemyCharacterDisplayPreview() {
    FalloutAppTheme {
        val character = Character("Bob")
        EnemyCharacterDisplay(
            character,
            {},
            {},
            {}
        )
    }
}
