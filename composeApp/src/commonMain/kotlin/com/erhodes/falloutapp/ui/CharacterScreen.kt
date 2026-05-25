package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.PlayerCharacter
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.Recipe
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.add_perk
import falloutapp.composeapp.generated.resources.backpack_24dp
import falloutapp.composeapp.generated.resources.craft
import falloutapp.composeapp.generated.resources.craft_failed
import falloutapp.composeapp.generated.resources.craft_failed_message
import falloutapp.composeapp.generated.resources.craft_success
import falloutapp.composeapp.generated.resources.discard
import falloutapp.composeapp.generated.resources.done
import falloutapp.composeapp.generated.resources.inventory
import falloutapp.composeapp.generated.resources.learn_recipe
import falloutapp.composeapp.generated.resources.loadout
import falloutapp.composeapp.generated.resources.perks
import falloutapp.composeapp.generated.resources.recipes
import falloutapp.composeapp.generated.resources.remove_perk
import falloutapp.composeapp.generated.resources.work_24dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterScreen(state: CharacterUiState,
                    onTakeDamage: (Int) -> Unit,
                    onHealDamage: (Int) -> Unit,
                    onRepair: (Int) -> Unit,
                    onModifyStress: (Int) -> Unit,
                    onModifyFear: (Int) -> Unit,
                    onModifyFatigue: (Int) -> Unit,
                    onModifyRadiation: (Int) -> Unit,
                    onGainMilestone: () -> Unit,
                    onAddPerk: () -> Unit,
                    onRemovePerk: (Perk) -> Unit,
                    onLearnRecipe: () -> Unit,
                    onRemoveRecipe: (Recipe) -> Unit,
                    onCraftRecipe: (Recipe) -> Unit,
                    onAddCraftedToLoadout: (ItemTemplate) -> Unit,
                    onAddCraftedToInventory: (ItemTemplate) -> Unit,
                    onEquipItem: (Item) -> Unit,
                    onUnequipItem: (Item) -> Unit,
                    onDiscardItem: (Item) -> Unit,
                    onIncreaseItem: (Item, Int) -> Unit,
                    onDecreaseItem: (Item, Int) -> Unit,
                    onAddItem: () -> Unit,
                    onEditName: (String) -> Unit,
                    onModifyCondition: (Condition, Int) -> Unit,
                    onManageConditions: () -> Unit
) {
    val character = state.character
    val editable = state.editable
    var showEditNameDialog by remember { mutableStateOf(false) }
    var craftedItem by remember { mutableStateOf<ItemTemplate?>(null) }
    var craftErrorRecipe by remember { mutableStateOf<Recipe?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 10.dp, start = 5.dp)
                    .weight(1f)
            )
            if (editable) {
                IconButton(
                    onClick = { showEditNameDialog = true },
                    modifier = Modifier.padding(start = Dimens.paddingMedium)
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit Name"
                    )
                }
            }
        }

        if (showEditNameDialog) {
            var newName by remember { mutableStateOf(character.name) }
            AlertDialog(
                onDismissRequest = { showEditNameDialog = false },
                title = { Text("Edit Character Name") },
                text = {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Name") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        onEditName(newName)
                        showEditNameDialog = false
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditNameDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
        CharacterStatsPanel(character)

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
        if (editable) {
            Button(
                onClick = onAddPerk,
                enabled = character.perks.size < character.level,
                modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
            ) {
                Text(stringResource(Res.string.add_perk))
            }
        }

        // Vitals
        VitalsScreen(
            characterState = state,
            onTakeDamage = onTakeDamage,
            onHealDamage = onHealDamage,
            onRepair = onRepair,
            onModifyStress = onModifyStress,
            onModifyFear = onModifyFear,
            onModifyFatigue = onModifyFatigue,
            onModifyRadiation = onModifyRadiation,
            onModifyCondition = onModifyCondition,
            onManageConditions = onManageConditions
        )

        ProgressionPanel(
            state = state,
            onGainMilestone = onGainMilestone
        )

        // Recipes
        Header(
            text = stringResource(Res.string.recipes),
            secondaryText = "${character.recipes.size}/${character.intelligence}"
        )
        Column(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
        ) {
            character.recipes.forEach { recipe ->
                RecipePanel(
                    recipe = recipe,
                    buttonLabel = if (editable) stringResource(Res.string.craft) else null,
                    buttonEnabled = editable && character.canCraftRecipe(recipe),
                    onClick = {
                        if (character.canCraftRecipe(recipe)) {
                            onCraftRecipe(recipe)
                            craftedItem = recipe.itemTemplate
                        } else {
                            craftErrorRecipe = recipe
                        }
                    },
                    onDelete = { onRemoveRecipe(recipe) }
                )
            }
        }
        if (editable) {
            Button(
                onClick = onLearnRecipe,
                enabled = character.recipes.size < character.intelligence,
                modifier = Modifier.padding(horizontal = Dimens.paddingSmall)
            ) {
                Text(stringResource(Res.string.learn_recipe))
            }
        }

        craftedItem?.let { template ->
            val canFitLoadout = character.loadoutWeight + template.load <= character.loadoutLimit
            AlertDialog(
                onDismissRequest = { craftedItem = null },
                title = { Text(stringResource(Res.string.craft_success)) },
                text = { Text("You have crafted ${template.name}. Where should it be placed?") },
                confirmButton = {
                    Row {
                        TextButton(
                            enabled = canFitLoadout,
                            onClick = {
                                onAddCraftedToLoadout(template)
                                craftedItem = null
                            }
                        ) { Text(stringResource(Res.string.loadout)) }
                        TextButton(onClick = {
                            onAddCraftedToInventory(template)
                            craftedItem = null
                        }) { Text(stringResource(Res.string.inventory)) }
                        TextButton(onClick = { craftedItem = null }) {
                            Text(stringResource(Res.string.discard))
                        }
                    }
                }
            )
        }

        if (craftErrorRecipe != null) {
            AlertDialog(
                onDismissRequest = { craftErrorRecipe = null },
                title = { Text(stringResource(Res.string.craft_failed)) },
                text = { Text(stringResource(Res.string.craft_failed_message)) },
                confirmButton = {
                    TextButton(onClick = { craftErrorRecipe = null }) {
                        Text(stringResource(Res.string.done))
                    }
                }
            )
        }

        // Loadout
        Header(
            text = stringResource(Res.string.loadout),
            secondaryText = "${character.loadoutWeight}/${character.loadoutLimit}",
            icon = Res.drawable.work_24dp
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
                    onIncreaseItem = { i, count -> onIncreaseItem(i, count) },
                    onDecreaseItem = { i, count -> onDecreaseItem(i, count) },
                    primaryButtonIcon = Res.drawable.backpack_24dp,
                    primaryButtonAction = { onUnequipItem(item) },
                    onDiscardItem = { onDiscardItem(item) }
                )
            }
        }

        // Inventory
        Header(
            text = stringResource(Res.string.inventory),
            secondaryText = "${character.inventoryWeight}/${character.inventoryLimit}",
            icon = Res.drawable.backpack_24dp
        )
        Column (
            modifier = Modifier
                .padding(horizontal = Dimens.paddingMedium)
        ) {
            character.inventory.forEach { item ->
                ItemPanel(
                    characterUiState = state,
                    item = item,
                    onIncreaseItem = { i, count -> onIncreaseItem(i, count) },
                    onDecreaseItem = { i, count -> onDecreaseItem(i, count) },
                    primaryButtonIcon = Res.drawable.work_24dp,
                    primaryButtonAction = { onEquipItem(item) },
                    onDiscardItem = { onDiscardItem(item) }
                )
            }
        }

        if (editable) {
            Button(
                onClick = onAddItem,
                modifier = Modifier.padding(start = Dimens.paddingMedium, bottom = Dimens.paddingMedium)
            ) {
                Text("Add Item")
            }
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
    onIncreaseItem: (Item, Int) -> Unit,
    onDecreaseItem: (Item, Int) -> Unit
    ) {
    // needed to force recomposition
    @Suppress("UnusedVariable", "unused") val character = characterUiState.character
    when (item) {
        is StackableItem -> {
            StackableItemPanel(
                stackable = item,
                count = item.count,
                increaseButton = { onIncreaseItem(item, it) },
                decreaseButton = { onDecreaseItem(item, it) },
                buttonIcon = primaryButtonIcon,
                buttonAction = { primaryButtonAction(item) },
                secondaryButtonIcon = Icons.Filled.Delete,
                secondaryButtonAction = { onDiscardItem(item) }
            )
        }
        is Weapon -> {
            WeaponPanel(
                weapon = item,
                ammo = item.ammo,
                increaseButton = { onIncreaseItem(item, it) },
                decreaseButton = { onDecreaseItem(item, it) },
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

@Preview
@Composable
fun CharacterScreenPreview() {
    val character = PlayerCharacter("Tom")
    val armor = Armor(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ARMOR_LEATHER), 0)
    val banner = BasicItem(ItemDataSource.getItemTemplateById(ItemDataSource.ID_BATTLE_STANDARD))
    character.addItemToInventory(armor)
    character.addItemToInventory(banner)
    character.equipItem(armor)
    FalloutAppTheme {
        CharacterScreen(
            state = CharacterUiState(character),
            onTakeDamage = {},
            onHealDamage = {},
            onRepair = {},
            onModifyStress = {},
            onModifyFear = {},
            onModifyFatigue = {},
            onModifyRadiation = {},
            onGainMilestone = {},
            onAddPerk = {},
            onRemovePerk = {},
            onLearnRecipe = {},
            onRemoveRecipe = {},
            onCraftRecipe = {},
            onAddCraftedToLoadout = {},
            onAddCraftedToInventory = {},
            onEquipItem = {},
            onUnequipItem = {},
            onDiscardItem = {},
            onIncreaseItem = { _, _ -> },
            onDecreaseItem = { _, _ -> },
            onAddItem = {},
            onEditName = {},
            onModifyCondition = { _, _ -> },
            onManageConditions = {},
        )
    }
}