package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.*
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterScreen(state: CharacterUiState,
                    onTakeDamage: (Int) -> Unit,
                    onHealDamage: (Int) -> Unit,
                    onRepair: (Int) -> Unit,
                    onModifyStress: (Int) -> Unit,
                    onModifyFatigue: (Int) -> Unit,
                    onModifyRadiation: (Int) -> Unit,
                    onGainMilestone: () -> Unit,
                    onAddPerk: () -> Unit,
                    onRemovePerk: (Perk) -> Unit,
                    onEquipItem: (Item) -> Unit,
                    onUnequipItem: (Item) -> Unit,
                    onIncreaseItem: (Item) -> Unit,
                    onDecreaseItem: (Item) -> Unit,
                    onAddItem: () -> Unit
) {
    val character = state.character
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // Vitals
        VitalsScreen(
            characterState = state,
            onTakeDamage = onTakeDamage,
            onHealDamage = onHealDamage,
            onRepair = onRepair,
            onModifyStress = onModifyStress,
            onModifyFatigue = onModifyFatigue,
            onModifyRadiation = onModifyRadiation
        )

        // SPECIAL
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Stats.entries.forEach {
                SpecialPanel(
                    title = stringResource(it.letter),
                    value = character.getStatByOrdinal(it.ordinal),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        }
        HorizontalDivider(thickness = 2.dp)

        //Skills
        Text(stringResource(Res.string.skills), style = MaterialTheme.typography.titleLarge)
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
        Row {
            Text(
                text = stringResource(Res.string.perks),
                style = MaterialTheme.typography.titleLarge
            )

            Text("${character.perks.size}/${character.level}")
        }

        character.perks.forEach { perk ->
            Row {
                PerkPanel(
                    perk = perk,
                    buttonEnabled = true,
                    buttonLabel = stringResource(Res.string.remove_perk),
                    onClick = { onRemovePerk(perk) }
                )
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
            Text(
                text = "Loadout",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${character.load}/${character.loadoutLimit}",
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(thickness = 2.dp)
        character.equippedArmor?.let {
            ArmorDisplay(it, stringResource(Res.string.unequip), { onUnequipItem(it) })
        }
        character.loadout.forEach {
            // todo figure out how to reuse this code in the inventory section
            //  the problem here is that I need to pass in a new value to trigger recomposition
            //  if I just pass in the item, compose will see the same item and do nothing
            if (it is StackableItem) {
                StackableItemPanel(
                    it,
                    it.count,
                    increaseButton = { onIncreaseItem(it) },
                    decreaseButton = { onDecreaseItem(it) },
                    buttonLabel = stringResource(Res.string.unequip),
                    buttonAction = { onUnequipItem(it) },
                )
            } else if (it is Weapon) {
                WeaponPanel(
                    it,
                    it.ammo,
                    increaseButton = { onIncreaseItem(it) },
                    decreaseButton = { onDecreaseItem(it) },
                    buttonLabel = stringResource(Res.string.unequip),
                    buttonAction = { onUnequipItem(it) },
                )
            } else if (it is Armor) {
                ArmorDisplay(
                    armor = it,
                    buttonLabel = stringResource(Res.string.unequip),
                    buttonAction = { onUnequipItem(it) },
                )
            } else {
                ItemDisplay(
                    item = it,
                    buttonLabel = stringResource(Res.string.unequip),
                    buttonAction = { onUnequipItem(it) },
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Inventory", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "${character.inventoryWeight}/${character.inventoryLimit}",
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(thickness = 2.dp)
        character.inventory.forEach {
            if (it is StackableItem) {
                StackableItemPanel(
                    it,
                    it.count,
                    increaseButton = { onIncreaseItem(it) },
                    decreaseButton = { onDecreaseItem(it) },
                    buttonLabel = stringResource(Res.string.equip),
                    buttonAction = { onEquipItem(it) },
                )
            } else if (it is Weapon) {
                WeaponPanel(
                    it,
                    it.ammo,
                    increaseButton = { onIncreaseItem(it) },
                    decreaseButton = { onDecreaseItem(it) },
                    buttonLabel = stringResource(Res.string.equip),
                    buttonAction = { onEquipItem(it) },
                )
            } else if (it is Armor) {
                ArmorDisplay(
                    armor = it,
                    buttonLabel = stringResource(Res.string.equip),
                    buttonAction = { onEquipItem(it) },
                )
            } else {
                ItemDisplay(
                    item = it,
                    buttonLabel = stringResource(Res.string.equip),
                    buttonAction = { onEquipItem(it) },
                )
            }
        }

        Button(
            onClick = onAddItem
        ) {
            Text("Add Item")
        }
    }
}

@Composable
fun SpecialPanel(title: String, value: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "$value",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(widthDp = 800)
@Composable
fun CharacterScreenPreview() {
    val character = Character("Tom")
//    val armor =Item(ItemRepository.LEATHER_ARMOR)
//    character.addItemToInventory(armor)
//    character.addItemToInventory(Item(ItemRepository.BANNER))
//    character.equipItem(armor)
    FalloutAppTheme {
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
            {},
            {},
            {},
            {},
            {},
            {}
        )
    }
}