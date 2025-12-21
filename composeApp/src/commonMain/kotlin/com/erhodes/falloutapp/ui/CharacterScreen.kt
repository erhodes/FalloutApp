package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.*
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import falloutapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

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

        //Skills
        Header(stringResource(Res.string.skills))
        Row(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
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

        //Perks
        Header(
            text = stringResource(Res.string.perks),
            secondaryText = "${character.perks.size}/${character.level}"
        )

        character.perks.forEach { perk ->
            Row {
                PerkPanel(
                    perk = perk,
                    buttonEnabled = true,
                    buttonLabel = stringResource(Res.string.remove_perk),
                    onClick = { onRemovePerk(perk) },
                    modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
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

        ProgressionPanel(
            state = state,
            onGainMilestone = onGainMilestone
        )

        // Loadout
        Header(
            text = stringResource(Res.string.loadout),
            secondaryText = "${character.loadoutWeight}/${character.loadoutLimit}"
        )
        Column (
            Modifier
                .padding(horizontal = Dimens.paddingMedium)
        ) {
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
        }

        // Inventory
        Header(
            text = stringResource(Res.string.inventory),
            secondaryText = "${character.inventoryWeight}/${character.inventoryLimit}"
        )
        Column (
            modifier = Modifier
                .padding(horizontal = Dimens.paddingMedium)
        ) {
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
        }

        Button(
            onClick = onAddItem,
            modifier = Modifier.padding(start = Dimens.paddingMedium, bottom = Dimens.paddingMedium)
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
    @Suppress("UnusedVariable", "unused") val character = characterUiState.character
    when (item) {
        is StackableItem -> {
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
        }
        is Weapon -> {
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
        }
        is Armor -> {
            ArmorDisplay(
                armor = item,
                buttonIcon = primaryButtonIcon,
                buttonAction = { primaryButtonAction(item) },
                secondaryButtonIcon = Icons.Filled.Delete,
                secondaryButtonAction = { onDiscardItem(item) }
            )
        }
        else -> {
            ItemDisplay(
                item = item,
                buttonIcon = primaryButtonIcon,
                buttonAction = { primaryButtonAction(item) },
                secondaryButtonIcon = Icons.Filled.Delete,
                secondaryButtonAction = { onDiscardItem(item) }
            )
        }
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
            modifier = Modifier.padding(6.dp)
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

//@Preview
//@Composable
//fun CharacterScreenPreview() {
//    val character = Character("Tom")
////    val armor =Item(ItemRepository.LEATHER_ARMOR)
////    character.addItemToInventory(armor)
////    character.addItemToInventory(Item(ItemRepository.BANNER))
////    character.equipItem(armor)
//    FalloutAppTheme {
//        CharacterScreen(
//            CharacterUiState(character),
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {},
//            {}
//        )
//    }
//}