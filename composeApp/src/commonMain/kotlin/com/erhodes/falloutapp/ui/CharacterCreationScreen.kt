package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.presentation.CharacterCreationUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import falloutapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterCreationScreen(
    uiState: CharacterCreationUiState,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
    onMajorClicked: (Int) -> Unit,
    onMinorClicked: (Int) -> Unit,
    onComplete: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
    ) {
        var text by remember { mutableStateOf("") }
        val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Name") }
        )

        Text(
            text = stringResource(Res.string.special),
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillWidthOfParent(Dimens.paddingSmall))
        Text("Points remaining ${uiState.pointsRemaining}/7")


        if (windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
            Row {
                for (i in 0..6) {
                    SpecialAllocation(
                        label = stringResource(Stats.entries[i].displayName),
                        value = uiState.stats[i],
                        pointsRemaining = uiState.pointsRemaining > 0,
                        onIncrement = { onIncrement(i) },
                        onDecrement = { onDecrement(i) }
                    )
                }
            }
        } else {
            Column {
                Row {
                    for (i in 0..2) {
                        SpecialAllocation(
                            label = stringResource(Stats.entries[i].displayName),
                            value = uiState.stats[i],
                            pointsRemaining = uiState.pointsRemaining > 0,
                            onIncrement = { onIncrement(i) },
                            onDecrement = { onDecrement(i) }
                        )
                    }
                }
                Row {
                    for (i in 3..5) {
                        SpecialAllocation(
                            label = stringResource(Stats.entries[i].displayName),
                            value = uiState.stats[i],
                            pointsRemaining = uiState.pointsRemaining > 0,
                            onIncrement = { onIncrement(i) },
                            onDecrement = { onDecrement(i) }
                        )
                    }
                }
                Row {
                    for (i in 6..6) {
                        SpecialAllocation(
                            label = stringResource(Stats.entries[i].displayName),
                            value = uiState.stats[i],
                            pointsRemaining = uiState.pointsRemaining > 0,
                            onIncrement = { onIncrement(i) },
                            onDecrement = { onDecrement(i) }
                        )
                    }
                }
            }
        }


        Text(
            text = stringResource(Res.string.skills),
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillWidthOfParent(Dimens.paddingSmall))
        Text("Majors remaining: ${uiState.majorsRemaining}  Minors remaining: ${uiState.minorsRemaining}")

        Row {
            Column(
                modifier = Modifier.padding(end = 10.dp)
            ) {
                for (i in 0.. 5) {
                    SkillAllocationPanel(
                        ordinal =  i,
                        value = uiState.skills[i],
                        majorEnabled = (uiState.majorsRemaining > 0),
                        minorEnabled = (uiState.minorsRemaining > 0),
                        onSelectMajor = onMajorClicked,
                        onSelectMinor = onMinorClicked
                    )
                }
            }
            Column {
                for (i in 6.. 11) {
                    SkillAllocationPanel(
                        ordinal = i,
                        value = uiState.skills[i],
                        majorEnabled = (uiState.majorsRemaining > 0),
                        minorEnabled = (uiState.minorsRemaining > 0),
                        onSelectMajor = onMajorClicked,
                        onSelectMinor = onMinorClicked)
                }
            }
        }

        Button(
            onClick = { onComplete(text) },
            enabled = text.isNotEmpty()
        ) {
            Text("Done")
        }
    }
}

@Composable
fun SkillAllocationPanel(ordinal: Int, value: Int, majorEnabled: Boolean, minorEnabled: Boolean, onSelectMajor: (Int) -> Unit, onSelectMinor: (Int) -> Unit) {
    Column {
        Text(stringResource(Skills.entries[ordinal].description))
        Row {
            Button(
                enabled = majorEnabled || value == 5,
                onClick = { onSelectMajor(ordinal) }
            ) {
                if (value == 5) {
                    Text(stringResource(Res.string.unselect))
                } else {
                    Text(stringResource(Res.string.major))
                }
            }
            Button(
                enabled = minorEnabled || value == 4,
                onClick = { onSelectMinor(ordinal) }
            ) {
                if (value == 4) {
                    Text(stringResource(Res.string.unselect))
                } else {
                    Text(stringResource(Res.string.minor))
                }
            }
        }
    }

}

@Composable
fun SpecialAllocation(label: String, value: Int, pointsRemaining: Boolean, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Column(
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Text(label)
        Text("$value")
        Row {
            Button(
                enabled = (value != 3 && pointsRemaining),
                onClick = onIncrement
            ) {
                Text("+")
            }
            Button(
                enabled = value != 1,
                onClick = onDecrement
            ) {
                Text("-")
            }
        }
    }
}

@Preview
@Composable
fun CharacterCreationScreenPreview() {
    MaterialTheme {
        CharacterCreationScreen(
            CharacterCreationUiState(),
            {},
            {},
            {},
            {}
        ) {  }
    }
}