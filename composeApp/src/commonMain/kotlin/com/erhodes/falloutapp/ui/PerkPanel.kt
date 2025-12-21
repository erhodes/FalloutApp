package com.erhodes.falloutapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.data.PerkDataSource
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.SkillRequirement
import com.erhodes.falloutapp.model.StatRequirement
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.requires
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PerkPanel(perk: Perk, buttonEnabled: Boolean, buttonLabel: String, onClick: () -> Unit, modifier: Modifier = Modifier, showRequirements: Boolean = false) {
    Column(
        modifier = modifier
    ) {
        var isExpanded by rememberSaveable { mutableStateOf(true) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceDim)
                .fillMaxWidth()
                .clickable{ isExpanded = !isExpanded }
                .padding(horizontal = Dimens.paddingMedium)
        ) {
            Text(
                text = perk.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(0.5f))
            Button(
                enabled = buttonEnabled,
                onClick = onClick
            ) {
                Text(buttonLabel)
            }
        }
        val color = MaterialTheme.colorScheme.surfaceDim
        AnimatedVisibility(
            modifier = Modifier
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val halfStroke = strokeWidth / 2

                    // Left border
                    drawLine(
                        color = color,
                        start = Offset(halfStroke, 0f),
                        end = Offset(halfStroke, size.height),
                        strokeWidth = strokeWidth
                    )

                    // Bottom border
                    drawLine(
                        color = color,
                        start = Offset(0f, size.height - halfStroke),
                        end = Offset(size.width, size.height - halfStroke),
                        strokeWidth = strokeWidth
                    )

                    // Right border
                    drawLine(
                        color = color,
                        start = Offset(size.width - halfStroke, 0f),
                        end = Offset(size.width - halfStroke, size.height),
                        strokeWidth = strokeWidth
                    )
                }
                .fillMaxWidth()
                .padding(horizontal = Dimens.paddingMedium),
            visible = isExpanded
        ) {
            Column {
                if (showRequirements) {
                    var requirementsString = stringResource(Res.string.requires)
                    perk.requirements.forEach { requirement ->
                        if (requirement is StatRequirement) {
                            requirementsString += " ${stringResource(requirement.stat.displayName)}:${requirement.min}"
                        } else if (requirement is SkillRequirement) {
                            requirementsString += " ${stringResource(requirement.skill.description)}:${requirement.min}"
                        }
                    }
                    Text(requirementsString)
                }
                Text(perk.description)
            }
        }
    }
}

@Preview
@Composable
fun PerkPanelPreview() {
    FalloutAppTheme {
        PerkPanel(
            perk = Perk("Hacker", "So good at hacking", 0),
            buttonEnabled = true,
            showRequirements = true,
            buttonLabel = "Select",
            onClick = {}
        )
    }
}