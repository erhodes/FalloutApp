package com.erhodes.falloutapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.data.PerkDataSource
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.SkillRequirement
import com.erhodes.falloutapp.model.StatRequirement
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceDim)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
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
        Column(modifier = Modifier.padding(horizontal = 5.dp)) {
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

@Preview
@Composable
fun PerkPanelPreview() {
    FalloutAppTheme {
        PerkPanel(
            perk = PerkDataSource.getPerkById(1),
            buttonEnabled = true,
            showRequirements = true,
            buttonLabel = "Select",
            onClick = {}
        )
    }
}