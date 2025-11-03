package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.repository.PerkRepository
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.select
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PerkSelectScreen(perks: Collection<Perk>, onSelect: (Perk) -> Unit) {
    Column {
        perks.forEach {
            PerkSelectPanel(it, onSelect)
        }
    }
}

@Composable
fun PerkSelectPanel(perk: Perk, onSelect: (Perk) -> Unit) {
    Row {
        PerkPanel(perk)
        Button(
            onClick = { onSelect(perk) }
        ) {
            Text(stringResource(Res.string.select))
        }
    }
}

@Preview
@Composable
fun PerkSelectScreenPreview() {
    MaterialTheme {
        PerkSelectScreen(PerkRepository.getAllPerks(), {})
    }
}