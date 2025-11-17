package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.data.PerkDataSource
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.select
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PerkSelectScreen(state: CharacterUiState, perks: Collection<Perk>, onSelect: (Perk) -> Unit) {
    Column {
        perks.forEach {
            PerkSelectPanel(it, state.character.qualifiesForPerk(it), onSelect)
        }
    }
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
        val perks = arrayListOf(PerkDataSource.getPerkById(0), PerkDataSource.getPerkById(1), PerkDataSource.getPerkById(2))
        PerkSelectScreen(CharacterUiState(Character("Tom")), perks, {})
    }
}