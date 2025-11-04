package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.presentation.CharacterUiState
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.add_perk
import falloutapp.composeapp.generated.resources.gain_milestone
import falloutapp.composeapp.generated.resources.heal
import falloutapp.composeapp.generated.resources.perks
import falloutapp.composeapp.generated.resources.remove_perk
import falloutapp.composeapp.generated.resources.repair
import falloutapp.composeapp.generated.resources.skills
import falloutapp.composeapp.generated.resources.take_damage
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterScreen(state: CharacterUiState,
                    onTakeDamage: (Int) -> Unit,
                    onHealDamage: (Int) -> Unit,
                    onRepair: (Int) -> Unit,
                    onGainMilestone: () -> Unit,
                    onAddPerk: () -> Unit,
                    onRemovePerk: (Perk) -> Unit,
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

        // Status
        Text("Damage Taken ${character.damageTaken}/5")

        Text("Armor ${character.getArmorDamage()}/${character.getArmorDurability()}")

        var amount by remember { mutableStateOf("") }
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Amount") }
        )
        Row {
            Button(
                onClick = { onTakeDamage(amount.toInt()) }
            ) {
                Text(stringResource(Res.string.take_damage))
            }
            Button(
                onClick = { onHealDamage(amount.toInt()) }
            ) {
                Text(stringResource(Res.string.heal))
            }
            Button(
                onClick = { onRepair(amount.toInt()) }
            ) {
                Text(stringResource(Res.string.repair))
            }
        }

        // SPECIAL
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpecialPanel("S", character.strength)
            SpecialPanel("P", character.perception)
            SpecialPanel("E", character.endurance)
            SpecialPanel("C", character.charisma)
            SpecialPanel("I", character.intelligence)
            SpecialPanel("A", character.agility)
            SpecialPanel("L", character.luck)
        }
        HorizontalDivider(thickness = 2.dp)

        //Skills
        Text(stringResource(Res.string.skills))
        Row {
            Column(
                modifier = Modifier.padding(end = 10.dp)
            ) {
                for (i in 0.. 5) {
                    Text("${stringResource(Skills.entries[i].description)}: ${character.skills[i]}")
                }
            }
            Column {
                for (i in 6.. 11) {
                    Text("${stringResource(Skills.entries[i].description)}: ${character.skills[i]}")
                }
            }
        }
        HorizontalDivider(thickness = 2.dp)

        //Perks
        Text("${stringResource(Res.string.perks)} ${character.perks.size}/${character.level}")
        character.perks.forEach { perk ->
            Row {
                PerkPanel(perk)
                Button(
                    onClick = { onRemovePerk(perk) }
                ) {
                    Text(stringResource(Res.string.remove_perk))
                }
            }
        }
        Button(
            onClick = onAddPerk,
            enabled = character.perks.size < character.level
        ) {
            Text(stringResource(Res.string.add_perk))
        }

        HorizontalDivider(thickness = 2.dp)
        Text("Level ${character.level} Milestones ${character.milestones}")
        Button(
            onClick = onGainMilestone
        ) {
            Text(stringResource(Res.string.gain_milestone))
        }

        // Inventory
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

@Composable
fun PerkPanel(perk: Perk) {
    Column {
        Text(perk.name)
        Text(perk.description)
    }
}

@Composable
fun SpecialPanel(title: String, value: Int) {
    Column {
        Text(title)
        Text("$value")
    }
}

@Preview
@Composable
fun CharacterScreenPreview() {
    val character = Character("Tom")
//    val armor =Item(ItemRepository.LEATHER_ARMOR)
//    character.addItemToInventory(armor)
//    character.addItemToInventory(Item(ItemRepository.BANNER))
//    character.equipItem(armor)
    MaterialTheme {
        CharacterScreen(
            CharacterUiState(character),
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        )
    }
}