package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.repository.ItemRepository
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterScreen(state: CharacterUiState,
                    onEquipItem: (Item) -> Unit,
                    onUnequipItem: (Item) -> Unit,
                    onAddItem: () -> Unit
) {
    val character = state.character
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = character.name,
            style = TextStyle.Default
        )
        HorizontalDivider(thickness = 2.dp)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Loadout")
            Text(
                text = "${character.load}/${character.loadoutLimit}",
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(thickness = 2.dp)
        character.loadout.forEach {
            ItemDisplay(
                item = it,
                buttonLabel = "Unequip",
                buttonAction = { onUnequipItem(it) }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inventory")
            Text(
                text = "${character.inventoryWeight}/${character.inventoryLimit}",
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(thickness = 2.dp)
        character.inventory.forEach {
            ItemDisplay(
                item = it,
                buttonLabel = "Equip",
                buttonAction = { onEquipItem(it) }
            )
        }


        Button(
            onClick = onAddItem
        ) {
            Text("Add Item")
        }
    }
}

@Preview
@Composable
fun CharacterScreenPreview() {
    val character = Character("Tom")
    val armor =Item(ItemRepository.LEATHER_ARMOR)
    character.addItemToInventory(armor)
    character.addItemToInventory(Item(ItemRepository.BANNER))
    character.equipItem(armor)
    MaterialTheme {
        CharacterScreen(
            CharacterUiState(character), {},  {}, {}
        )
    }
}