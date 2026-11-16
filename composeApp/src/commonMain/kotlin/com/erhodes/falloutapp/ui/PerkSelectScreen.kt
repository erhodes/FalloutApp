package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.Stats
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.presentation.PerkFilter
import com.erhodes.falloutapp.presentation.TierFilter
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.all
import falloutapp.composeapp.generated.resources.apply
import falloutapp.composeapp.generated.resources.cancel
import falloutapp.composeapp.generated.resources.filter
import falloutapp.composeapp.generated.resources.filter_perks
import falloutapp.composeapp.generated.resources.select
import falloutapp.composeapp.generated.resources.skills
import falloutapp.composeapp.generated.resources.stats
import falloutapp.composeapp.generated.resources.tier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PerkSelectScreen(state: CharacterUiState, perks: Collection<Perk>, onSelect: (Perk) -> Unit) {
    var filter by remember { mutableStateOf(PerkFilter()) }
    var showFilterDialog by remember { mutableStateOf(false) }

    Column {
        OutlinedButton(onClick = { showFilterDialog = true }) {
            Text(filterSummary(filter))
        }
        perks.filter(filter::matches).forEach {
            PerkSelectPanel(it, state.character.qualifiesForPerk(it), onSelect)
        }
    }

    if (showFilterDialog) {
        PerkFilterDialog(
            current = filter,
            onDismiss = { showFilterDialog = false },
            onApply = {
                filter = it
                showFilterDialog = false
            }
        )
    }
}

@Composable
private fun tierLabel(tier: TierFilter): String = when (tier) {
    TierFilter.ALL -> stringResource(Res.string.all)
    else -> tier.tier.toString()
}

/** e.g. "Filter (Tier: All, Stats: A, I)"; the Stats part only appears when stats are selected. */
@Composable
private fun filterSummary(filter: PerkFilter): String {
    val parts = mutableListOf("${stringResource(Res.string.tier)}: ${tierLabel(filter.tier)}")
    if (filter.stats.isNotEmpty()) {
        // Sort by enum order so the summary reads in SPECIAL order regardless of click order.
        val letters = filter.stats.sortedBy { it.ordinal }.map { stringResource(it.letter) }
        parts += "${stringResource(Res.string.stats)}: ${letters.joinToString(", ")}"
    }
    if (filter.skills.isNotEmpty()) {
        val names = filter.skills.sortedBy { it.ordinal }.map { stringResource(it.description) }
        parts += "${stringResource(Res.string.skills)}: ${names.joinToString(", ")}"
    }
    return "${stringResource(Res.string.filter)} (${parts.joinToString(", ")})"
}

/** Edits a local copy of the filter; only Apply commits it. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PerkFilterDialog(
    current: PerkFilter,
    onDismiss: () -> Unit,
    onApply: (PerkFilter) -> Unit,
) {
    var pending by remember { mutableStateOf(current) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(Res.string.filter_perks)) },
        text = {
            // Three chip groups can outgrow a phone screen, so let the body scroll.
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(stringResource(Res.string.tier))
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)) {
                    TierFilter.entries.forEach { tier ->
                        FilterChip(
                            selected = tier == pending.tier,
                            onClick = { pending = pending.copy(tier = tier) },
                            label = { Text(tierLabel(tier)) }
                        )
                    }
                }
                Text(stringResource(Res.string.stats))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)) {
                    Stats.entries.forEach { stat ->
                        FilterChip(
                            selected = stat in pending.stats,
                            onClick = {
                                val stats = if (stat in pending.stats) pending.stats - stat else pending.stats + stat
                                pending = pending.copy(stats = stats)
                            },
                            label = { Text(stringResource(stat.displayName)) }
                        )
                    }
                }
                Text(stringResource(Res.string.skills))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)) {
                    Skills.entries.forEach { skill ->
                        FilterChip(
                            selected = skill in pending.skills,
                            onClick = {
                                val skills = if (skill in pending.skills) pending.skills - skill else pending.skills + skill
                                pending = pending.copy(skills = skills)
                            },
                            label = { Text(stringResource(skill.description)) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onApply(pending) }) {
                Text(stringResource(Res.string.apply))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.cancel))
            }
        }
    )
}

@Composable
fun PerkSelectPanel(perk: Perk, enabled: Boolean, onSelect: (Perk) -> Unit) {
    Row {
        PerkPanel(
            perk = perk,
            buttonEnabled = enabled,
            buttonLabel = stringResource(Res.string.select),
            onClick = { onSelect(perk) },
            showRequirements = true
        )
    }
}

@Preview
@Composable
fun PerkSelectScreenPreview() {
    FalloutAppTheme {
        // Data source perks are all tier -1 (hidden), so build visible ones here.
        val perks = arrayListOf(
            Perk("Hacker", "So good at hacking", 0, tier = 1),
            Perk("Deadeye", "Hits what it aims at", 1, tier = 1),
            Perk("Grenadier", "Bigger booms", 2, tier = 2),
        )
        PerkSelectScreen(CharacterUiState(PlayerCharacter("Tom")), perks, {})
    }
}
