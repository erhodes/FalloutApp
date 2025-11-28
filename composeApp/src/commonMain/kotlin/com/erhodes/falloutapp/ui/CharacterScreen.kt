package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
 
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.*
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.DrawableResource
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
                    onDiscardItem: (Item) -> Unit,
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
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
        Text(text = stringResource(Res.string.skills),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
        )
        Row(
            modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
        ) {
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
        Row(
            modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
        ) {
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
            enabled = character.perks.size < character.level,
            modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
        ) {
            Text(stringResource(Res.string.add_perk))
        }

        ProgressionPanel(
            state = state,
            onGainMilestone = onGainMilestone
        )

        // Inventory
        HorizontalDivider(thickness = 2.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = Dimens.paddingSmall)
        ) {
            Text(
                text = "Loadout",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${character.loadoutWeight}/${character.loadoutLimit}",
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(thickness = 2.dp)
        character.equippedArmor?.let {
            ArmorDisplay(
                armor = it,
                buttonIcon = Res.drawable.backpack_24dp,
                buttonAction = { onUnequipItem(it) },
                secondaryButtonIcon = Icons.Filled.Delete,
                secondaryButtonAction = { onDiscardItem(it) }
            )
        }
        character.loadout.forEach { item ->
            ItemPanel(
                characterUiState = state,
                item = item,
                onIncreaseItem = { onIncreaseItem(it) },
                onDecreaseItem = { onDecreaseItem(it) },
                primaryButtonIcon = Res.drawable.backpack_24dp,
                primaryButtonAction = { onUnequipItem(it) },
                onDiscardItem = { onDiscardItem(it) }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Inventory",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
            )
            Text(
                text = "${character.inventoryWeight}/${character.inventoryLimit}",
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(thickness = 2.dp)
        character.inventory.forEach { item ->
            ItemPanel(
                characterUiState = state,
                item = item,
                onIncreaseItem = { onIncreaseItem(it) },
                onDecreaseItem = { onDecreaseItem(it) },
                primaryButtonIcon = Res.drawable.work_24dp,
                primaryButtonAction = { onEquipItem(it) },
                onDiscardItem = { onDiscardItem(it) }
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
fun ItemPanel(
    characterUiState: CharacterUiState,
    item: Item,
    primaryButtonIcon: DrawableResource,
    primaryButtonAction: (Item) -> Unit,
    onDiscardItem: (Item) -> Unit,
    onIncreaseItem: (Item) -> Unit,
    onDecreaseItem: (Item) -> Unit
    ) {
    // needed to force recomposition
    val character = characterUiState.character
    if (item is StackableItem) {
        StackableItemPanel(
            item,
            item.count,
            increaseButton = { onIncreaseItem(item) },
            decreaseButton = { onDecreaseItem(item) },
            buttonIcon = primaryButtonIcon,
            buttonAction = { primaryButtonAction(item) },
            secondaryButtonIcon = Icons.Filled.Delete,
            secondaryButtonAction = { onDiscardItem(item) }
        )
    } else if (item is Weapon) {
        WeaponPanel(
            item,
            item.ammo,
            increaseButton = { onIncreaseItem(item) },
            decreaseButton = { onDecreaseItem(item) },
            buttonIcon = primaryButtonIcon,
            buttonAction = { primaryButtonAction(item) },
            secondaryButtonIcon = Icons.Filled.Delete,
            secondaryButtonAction = { onDiscardItem(item) }
        )
    } else if (item is Armor) {
        ArmorDisplay(
            armor = item,
            buttonIcon = primaryButtonIcon,
            buttonAction = { primaryButtonAction(item) },
            secondaryButtonIcon = Icons.Filled.Delete,
            secondaryButtonAction = { onDiscardItem(item) }
        )
    } else {
        ItemDisplay(
            item = item,
            buttonIcon = primaryButtonIcon,
            buttonAction = { primaryButtonAction(item) },
            secondaryButtonIcon = Icons.Filled.Delete,
            secondaryButtonAction = { onDiscardItem(item) }
        )
    }
}

@Composable
fun SpecialPanel(title: String, value: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(4.dp),
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$value",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview()
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
            {},
            {}
        )
    }
}