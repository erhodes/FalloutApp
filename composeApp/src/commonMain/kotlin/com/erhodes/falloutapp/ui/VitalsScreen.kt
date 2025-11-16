package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.fatigue
import falloutapp.composeapp.generated.resources.heal
import falloutapp.composeapp.generated.resources.radiation
import falloutapp.composeapp.generated.resources.repair
import falloutapp.composeapp.generated.resources.stress
import falloutapp.composeapp.generated.resources.take_damage
import falloutapp.composeapp.generated.resources.toughness
import falloutapp.composeapp.generated.resources.vitals
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun VitalsScreen(
    characterState: CharacterUiState,
    onTakeDamage: (Int) -> Unit,
    onHealDamage: (Int) -> Unit,
    onRepair: (Int) -> Unit,
    onModifyStress: (Int) -> Unit,
    onModifyFatigue: (Int) -> Unit,
    onModifyRadiation: (Int) -> Unit
) {
    val character = characterState.character
    Column {
        Text(
            text = stringResource(Res.string.vitals),
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(thickness = 2.dp)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column {
                Text("Damage Taken ${character.damageTaken}/${Character.MAX_STRESS}")

                Text("Armor ${character.getArmorDamage()}/${character.getArmorDurability()}")
                Text("${stringResource(Res.string.toughness)} ${character.getArmorToughness()}")

                var amount by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Amount") }
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            onTakeDamage(amount.toInt())
                            amount = ""
                        }
                    ) {
                        Text(stringResource(Res.string.take_damage))
                    }
                    Button(
                        onClick = {
                            onHealDamage(amount.toInt())
                            amount = ""
                        }
                    ) {
                        Text(stringResource(Res.string.heal))
                    }
                    Button(
                        onClick = {
                            onRepair(amount.toInt())
                            amount = ""
                        }
                    ) {
                        Text(stringResource(Res.string.repair))
                    }
                }
            }
            Column {
                Row {
                    Text(
                        text = "${stringResource(Res.string.stress)} ${character.stress}/5",
                        modifier = Modifier.fillMaxWidth(0.1f)
                    )
                    PlusMinusButtons(
                        onIncrease = { onModifyStress(1) },
                        onDecrease = { onModifyStress(-1) }
                    )
                }
                Row {
                    Text(
                        text = "${stringResource(Res.string.fatigue)}: ${character.fatigue}",
                        modifier = Modifier.fillMaxWidth(0.1f))
                    PlusMinusButtons(
                        onIncrease = { onModifyFatigue(1) },
                        onDecrease = { onModifyFatigue(-1) }
                    )
                }
                Row {
                    Text(
                        text ="${stringResource(Res.string.radiation)}: ${character.radiation}",
                        modifier = Modifier.fillMaxWidth(0.1f)
                    )
                    PlusMinusButtons(
                        onIncrease = { onModifyRadiation(1) },
                        onDecrease = { onModifyRadiation(-1) }
                    )
                }
            }
        }
    }
}

@Composable
fun PlusMinusButtons(onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = onIncrease
        ) {
            Text("+")
        }
        Button(
            onClick = onDecrease
        ) {
            Text("-")
        }
    }
}

@Preview(widthDp = 600)
@Composable
fun VitalsScreenPreview() {
    FalloutAppTheme {
        VitalsScreen(
            CharacterUiState(Character("Tom")),
            {},
            {},
            {},
            {},
            {},
            {}
        )
    }
}