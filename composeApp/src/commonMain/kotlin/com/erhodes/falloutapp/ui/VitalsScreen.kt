package com.erhodes.falloutapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.FearLevel
import com.erhodes.falloutapp.model.condition.ConditionTemplate
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.model.condition.ScalableCondition
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
    onModifyFear: (Int) -> Unit,
    onModifyFatigue: (Int) -> Unit,
    onModifyRadiation: (Int) -> Unit,
    onModifyCondition: (Condition, Int) -> Unit,
    onManageConditions: () -> Unit
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
                        onModifyFear = onModifyFear,
                        onModifyFatigue = onModifyFatigue,
                        onModifyRadiation = onModifyRadiation
                    )
                    ConditionsPanel(
                        characterState = characterState,
                        onModifyConditionValue = onModifyCondition,
                        onManageConditions = onManageConditions
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
                        onModifyFear = onModifyFear,
                        onModifyFatigue = onModifyFatigue,
                        onModifyRadiation = onModifyRadiation
                    )
                    ConditionsPanel(
                        characterState = characterState,
                        onModifyConditionValue = onModifyCondition,
                        onManageConditions = onManageConditions
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
        Text(
            text = "Damage Taken ${character.damageTaken}/${Character.MAX_HEALTH}",
            color = if (character.isBloodied()) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )

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
    onModifyFear: (Int) -> Unit,
    onModifyFatigue: (Int) -> Unit,
    onModifyRadiation: (Int) -> Unit
    ) {
    val character = characterState.character
    val editable = characterState.editable
    Column {
        Row {
            Text(
                text = "${stringResource(Res.string.stress)} ${character.stress}/${Character.MAX_STRESS}",
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
                text = "${stringResource(Res.string.fear)}: ${character.fear}  ${stringResource(Res.string.courage)}: ${character.getCourage()}",
                color = when (character.getFearLevel()) {
                    FearLevel.STEADY -> MaterialTheme.colorScheme.onSurface
                    FearLevel.SHAKEN -> MaterialTheme.colorScheme.secondary
                    FearLevel.PANICKED -> MaterialTheme.colorScheme.error
                    FearLevel.BROKEN -> Color.Green
                },
                modifier = Modifier.fillMaxWidth(0.2f))
            if (editable) {
                PlusMinusButtons(
                    onIncrease = { onModifyFear(1) },
                    onDecrease = { onModifyFear(-1) }
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
fun ConditionsPanel(characterState: CharacterUiState, onModifyConditionValue: (Condition, Int) -> Unit, onManageConditions: () -> Unit) {
    Column {
        Text(
            text = "Conditions",
            style = MaterialTheme.typography.titleMedium
        )
        var showAbilityPopup by remember { mutableStateOf<Condition?>(null) }
        characterState.character.conditions.forEach { c ->
            Row {
                Text(
                    text = if (c is ScalableCondition) "${c.template.name} ${c.count}" else c.template.name,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable { showAbilityPopup = c },
                )
                if (c is ScalableCondition) {
                    PlusMinusButtons(
                        onIncrease = { onModifyConditionValue(c, 1) },
                        onDecrease = { onModifyConditionValue(c, -1) }
                    )
                }
            }
        }
        showAbilityPopup?.let {
            AlertDialog(
                onDismissRequest = { showAbilityPopup = null },
                title = { Text(it.template.title) },
                text = { Text(it.template.description) },
                confirmButton = {
                    TextButton(onClick = { showAbilityPopup = null }) {
                        Text("OK")
                    }
                }
            )
        }
        Button(
            onClick = {
                onManageConditions()
            }
        ) {
            Text(stringResource(Res.string.manage_conditions))
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
            {},
            {},
            {_, _ ->},
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
            {},
            {},
            onModifyCondition = {_, _ ->},
            {},
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
            {},
            {},
            {_, _ ->},
            {}
        )
    }
}

@Preview
@Composable
fun ConditionsPanelPreview() {
    val character = Character("Bob")
    val burning = Condition.buildNewCondition(ConditionTemplate.Burning)
    character.addCondition(burning)
    FalloutAppTheme {
        ConditionsPanel(
            characterState = CharacterUiState(character),
            onModifyConditionValue = { _, _ -> },
            onManageConditions = {}
        )
    }
}