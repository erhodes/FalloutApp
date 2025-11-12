package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.repository.PerkRepository
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PerkPanel(perk: Perk, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = perk.name,
            style = MaterialTheme.typography.titleMedium
        )
        Text(perk.description)
    }
}

@Preview
@Composable
fun PerkPanelPreview() {
    FalloutAppTheme {
        PerkPanel(PerkRepository.getPerkById(0))
    }
}