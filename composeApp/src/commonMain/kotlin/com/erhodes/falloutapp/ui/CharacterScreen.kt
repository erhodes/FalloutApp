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
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.Skills
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.util.AppLogger
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.add_perk
import falloutapp.composeapp.generated.resources.equip
import falloutapp.composeapp.generated.resources.gain_milestone
import falloutapp.composeapp.generated.resources.heal
import falloutapp.composeapp.generated.resources.perks
import falloutapp.composeapp.generated.resources.remove_perk
import falloutapp.composeapp.generated.resources.repair
import falloutapp.composeapp.generated.resources.skills
import falloutapp.composeapp.generated.resources.take_damage
import falloutapp.composeapp.generated.resources.unequip
import falloutapp.composeapp.generated.resources.vitals
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

        Text(
            text = stringResource(Res.string.vitals),
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(thickness = 2.dp)

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


//@Composable
//fun WeaponPanel(
//    weapon: Weapon,
//    ammo: Int,
//    increaseButton: () -> Unit,
//    decreaseButton: () -> Unit,
//    buttonLabel: String,
//    buttonAction: () -> Unit
//) {
//    Row(
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(weapon.name)
//
//        Column(
//            modifier = Modifier.weight(0.3f)
//        ) {
//            val spacing = 0.05f
//            Row {
//                Text("Successes", Modifier.weight(spacing))
//                Text("Damage", Modifier.weight(spacing))
//                Text("Ability", Modifier.weight(spacing))
//            }
//            for (i in 0 until 3) {
//                Row {
//                    Text("$i", Modifier.weight(spacing))
//                    Text("${weapon.damage[i]}", Modifier.weight(spacing))
//                    Text(weapon.ability[i], Modifier.weight(spacing))
//                }
//            }
//        }
//
//        if (weapon.magazineSize > 0) {
//            Text(
//                modifier = Modifier.padding(horizontal = 10.dp),
//                text = "Ammo $ammo/${weapon.magazineSize}"
//            )
//            Button(
//                onClick = increaseButton
//            ) {
//                Text("+")
//            }
//            Button(
//                onClick = decreaseButton
//            ) {
//                Text("-")
//            }
//        }
//        Button(
//            onClick = buttonAction
//        ) {
//            Text(buttonLabel)
//        }
//    }
//}

//@Composable
//fun StackableItemPanel(
//    stackable: StackableItem,
//    count: Int,
//    increaseButton: () -> Unit,
//    decreaseButton: () -> Unit,
//    buttonLabel: String,
//    buttonAction: () -> Unit
//) {
//    Row(
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(stackable.name)
//        Text(
//            modifier = Modifier.padding(horizontal = 10.dp),
//            text = "Quantity $count"
//        )
//        Button(
//            onClick = increaseButton
//        ) {
//            Text("+")
//        }
//        Button(
//            onClick = decreaseButton
//        ) {
//            Text("-")
//        }
//        Button(
//            onClick = buttonAction
//        ) {
//            Text(buttonLabel)
//        }
//    }
//}

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
            {},
            {},
            {},
            {},
            {},
            {}
        )
    }
}