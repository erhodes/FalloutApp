package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.*
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
        Header(stringResource(Res.string.vitals))
        Column(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
        ) {
            val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            if (windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PrimaryVitalsPanel(
                        characterState = characterState,
                        onTakeDamage = onTakeDamage,
                        onHealDamage = onHealDamage,
                        onRepair = onRepair
                    )
                    SecondaryVitalsPanel(
                        characterState = characterState,
                        onModifyStress = onModifyStress,
                        onModifyFatigue = onModifyFatigue,
                        onModifyRadiation = onModifyRadiation
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PrimaryVitalsPanel(
                        characterState = characterState,
                        onTakeDamage = onTakeDamage,
                        onHealDamage = onHealDamage,
                        onRepair = onRepair
                    )
                    SecondaryVitalsPanel(
                        characterState = characterState,
                        onModifyStress = onModifyStress,
                        onModifyFatigue = onModifyFatigue,
                        onModifyRadiation = onModifyRadiation
                    )
                }
            }
        }
    }
}

@Composable
fun PrimaryVitalsPanel(characterState: CharacterUiState,
                       onTakeDamage: (Int) -> Unit,
                       onHealDamage: (Int) -> Unit,
                       onRepair: (Int) -> Unit,) {
    val character = characterState.character
    val editable = characterState.editable
    Column {
        Text("Speed: ${character.speed}")
        Text("Damage Taken ${character.damageTaken}/${Character.MAX_STRESS}")

        Text("Armor ${character.getArmorDamage()}/${character.getArmorDurability()}")
        Text("${stringResource(Res.string.toughness)} ${character.getArmorToughness()}")

        if (editable) {
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
}

@Composable
fun SecondaryVitalsPanel(
    characterState: CharacterUiState,
    onModifyStress: (Int) -> Unit,
    onModifyFatigue: (Int) -> Unit,
    onModifyRadiation: (Int) -> Unit
    ) {
    val character = characterState.character
    val editable = characterState.editable
    Column {
        Row {
            Text(
                text = "${stringResource(Res.string.stress)} ${character.stress}/5",
                modifier = Modifier.fillMaxWidth(0.2f)
            )
            if (editable) {
                PlusMinusButtons(
                    onIncrease = { onModifyStress(1) },
                    onDecrease = { onModifyStress(-1) }
                )
            }
        }
        Row {
            Text(
                text = "${stringResource(Res.string.fatigue)}: ${character.fatigue}",
                modifier = Modifier.fillMaxWidth(0.2f))
            if (editable) {
                PlusMinusButtons(
                    onIncrease = { onModifyFatigue(1) },
                    onDecrease = { onModifyFatigue(-1) }
                )
            }
        }
        Row {
            Text(
                text ="${stringResource(Res.string.radiation)}: ${character.radiation}",
                modifier = Modifier.fillMaxWidth(0.2f)
            )
            if (editable) {
                PlusMinusButtons(
                    onIncrease = { onModifyRadiation(1) },
                    onDecrease = { onModifyRadiation(-1) }
                )
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

@Preview()
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

@Preview()
@Composable
fun VitalsScreenPreviewNonEditable() {
    FalloutAppTheme {
        VitalsScreen(
            CharacterUiState(Character("Tom"), false),
            {},
            {},
            {},
            {},
            {},
            {}
        )
    }
}

@Preview(widthDp = 900)
@Composable
fun VitalsScreenPreviewWide() {
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