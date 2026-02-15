package com.erhodes.falloutapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.model.ability.Ability
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.abilities
import falloutapp.composeapp.generated.resources.enterprise_off_24dp
import falloutapp.composeapp.generated.resources.passives
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GenericItemDisplay(
    title: String,
    summary: String,
    buttonIcon: DrawableResource,
    buttonAction: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryButtonIcon: ImageVector? = null,
    secondaryButtonAction: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = Dimens.paddingSmall)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceDim)
                .clickable{ isExpanded = !isExpanded }
                .padding(horizontal = Dimens.paddingSmall)
        ) {
            Column{
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = Dimens.paddingSmall)
                )
                Text(
                    text = summary
                )
            }
            Spacer(Modifier.weight(0.5f))
            IconButton(
                onClick = buttonAction,
            ) {
                Icon(
                    painter = painterResource(buttonIcon),
                    contentDescription = "Equip"
                )
            }
            if (secondaryButtonIcon != null && secondaryButtonAction != null) {
                IconButton(
                    onClick = secondaryButtonAction
                ) {
                    Icon(
                        imageVector = secondaryButtonIcon,
                        contentDescription = "Delete Item"
                    )
                }
            }
        }

        val color = MaterialTheme.colorScheme.surfaceDim
        AnimatedVisibility(
            modifier = Modifier
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val halfStroke = strokeWidth / 2

                    // Left border
                    drawLine(
                        color = color,
                        start = Offset(halfStroke, 0f),
                        end = Offset(halfStroke, size.height),
                        strokeWidth = strokeWidth
                    )

                    // Bottom border
                    drawLine(
                        color = color,
                        start = Offset(0f, size.height - halfStroke),
                        end = Offset(size.width, size.height - halfStroke),
                        strokeWidth = strokeWidth
                    )

                    // Right border
                    drawLine(
                        color = color,
                        start = Offset(size.width - halfStroke, 0f),
                        end = Offset(size.width - halfStroke, size.height),
                        strokeWidth = strokeWidth
                    )
                }
                .fillMaxWidth()
                .padding(horizontal = Dimens.paddingSmall),

            visible = isExpanded
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                content()
            }
        }
    }
}

@Composable
fun ItemDisplay(
    item: Item,
    buttonIcon: DrawableResource,
    buttonAction: () -> Unit,
    secondaryButtonIcon: ImageVector? = null,
    secondaryButtonAction: (() -> Unit)? = null
) {
    GenericItemDisplay(
        title = item.name,
        summary = "Load ${item.load}",
        buttonIcon = buttonIcon,
        buttonAction = buttonAction,
        secondaryButtonIcon = secondaryButtonIcon,
        secondaryButtonAction = secondaryButtonAction
    ) {
        Text(
            text = item.description
        )
    }
}

@Composable
fun ItemTemplateDisplay(template: ItemTemplate, buttonIcon: DrawableResource, buttonAction: () -> Unit) {
    GenericItemDisplay(
        title = template.name,
        summary = "Load: ${template.load}",
        buttonIcon = buttonIcon,
        buttonAction = buttonAction
    ) {
        Text(template.description)
    }
}

@Composable
fun ArmorDisplay(
    armor: Armor,
    buttonIcon: DrawableResource,
    buttonAction: () -> Unit,
    secondaryButtonIcon: ImageVector? = null,
    secondaryButtonAction: (() -> Unit)? = null
) {
    GenericItemDisplay(
        title = armor.name,
        summary = "Load: ${armor.load}  Durability: ${armor.damageTaken}/${armor.durability} Toughness:${armor.toughness}",
        buttonIcon = buttonIcon,
        buttonAction = buttonAction,
        secondaryButtonIcon = secondaryButtonIcon,
        secondaryButtonAction = secondaryButtonAction
    ) {
        Column {
            Text(armor.description)
            if (armor.abilities.isNotEmpty()) {
                PassiveAbilityList(stringResource(Res.string.abilities), armor.abilities)
            }
        }

    }
}

@Composable
fun WeaponPanel(
    weapon: Weapon,
    ammo: Int,
    increaseButton: (Int) -> Unit,
    decreaseButton: (Int) -> Unit,
    buttonIcon: DrawableResource,
    buttonAction: () -> Unit,
    secondaryButtonIcon: ImageVector? = null,
    secondaryButtonAction: (() -> Unit)? = null
) {
    GenericItemDisplay(
        title = weapon.name,
        summary = "Load: ${weapon.load} Range: ${weapon.range}",
        buttonIcon = buttonIcon,
        buttonAction = buttonAction,
        secondaryButtonIcon = secondaryButtonIcon,
        secondaryButtonAction = secondaryButtonAction
    ) {
        Column {
            Text(weapon.description)
            if (weapon.passive.isNotEmpty()) {
                PassiveAbilityList(stringResource(Res.string.passives), weapon.passive)
            }
            Row {
                Column(
                    modifier = Modifier.width(300.dp)
                ) {
                    val spacing = 0.03f
                    Row(
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text ="Successes",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(spacing)
                        )
                        Text(
                            text = "Damage",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(spacing)
                        )
                        Text(
                            text = "Ability",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(spacing)
                        )
                    }
                    for (i in 0 until 3) {
                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text("$i", Modifier.weight(spacing))
                            Text("${weapon.damage[i]}", Modifier.weight(spacing))
//                            Text(weapon.ability[i], Modifier.weight(spacing))
                            var showAbilityPopup by remember { mutableStateOf(false) }
                            Text(
                                text = weapon.ability[i].title,
                                modifier = Modifier.clickable { showAbilityPopup = true },
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            )
                            if (showAbilityPopup) {
                                AlertDialog(
                                    onDismissRequest = { showAbilityPopup = false },
                                    title = { Text(weapon.ability[i].title) },
                                    text = { Text(weapon.ability[i].description) },
                                    confirmButton = {
                                        TextButton(onClick = { showAbilityPopup = false }) {
                                            Text("OK")
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
            if (weapon.magazineSize > 0) {
                Row {
                    var textValue by remember { mutableStateOf("1") }
                    val amount = textValue.toIntOrNull() ?: 1
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Quantity $ammo. Up to ${weapon.magazineSize} per Load"
                    )
                    Button(
                        onClick = { increaseButton(amount) }
                    ) {
                        Text("+")
                    }
                    OutlinedTextField(
                        value = textValue,
                        onValueChange = { textValue = it },
                        modifier = Modifier.width(70.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Button(
                        enabled = ammo > 0,
                        onClick = { decreaseButton(amount) }
                    ) {
                        Text("-")
                    }
                }
            }
        }
    }
}

@Composable
fun StackableItemPanel(
    stackable: StackableItem,
    count: Int,
    increaseButton: (Int) -> Unit,
    decreaseButton: (Int) -> Unit,
    buttonIcon: DrawableResource,
    buttonAction: () -> Unit,
    secondaryButtonIcon: ImageVector? = null,
    secondaryButtonAction: (() -> Unit)? = null
) {
    var textValue by remember { mutableStateOf("1") }
    val amount = textValue.toIntOrNull() ?: 1

    GenericItemDisplay(
        title = stackable.name,
        summary = "Load: ${stackable.load} Up to ${stackable.max} per load",
        buttonIcon = buttonIcon,
        buttonAction = buttonAction,
        secondaryButtonIcon = secondaryButtonIcon,
        secondaryButtonAction = secondaryButtonAction
    ) {
        Column {
            Text(stackable.description)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Quantity $count"
                )
                Button(
                    onClick = { increaseButton(amount) }
                ) {
                    Text("+")
                }
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    modifier = Modifier.width(70.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Button(
                    enabled = count > 0,
                    onClick = { decreaseButton(amount) }
                ) {
                    Text("-")
                }
            }
        }
    }
}

@Composable
fun PassiveAbilityList(title: String, abilities: List<Ability>) {
    Row {
        Text(
            text = "$title: ",
            fontWeight = FontWeight.Bold
        )
        abilities.forEach { ability ->
            var showAbilityPopup by remember { mutableStateOf(false) }
            Text(
                text = ability.title + if (abilities.last() != ability) ", " else "",
                modifier = Modifier.clickable { showAbilityPopup = true },
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
            if (showAbilityPopup) {
                AlertDialog(
                    onDismissRequest = { showAbilityPopup = false },
                    title = { Text(ability.title) },
                    text = { Text(ability.description) },
                    confirmButton = {
                        TextButton(onClick = { showAbilityPopup = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

@Preview(widthDp = 700)
@Composable
fun WeaponPanelPreview() {
    FalloutAppTheme {
        WeaponPanel(
            weapon = Weapon(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ASSAULT_RIFLE), 0),
            ammo = 0,
            increaseButton = {},
            decreaseButton = {},
            buttonIcon = Res.drawable.enterprise_off_24dp,
            buttonAction = {},
            secondaryButtonIcon = Icons.Filled.Delete,
            secondaryButtonAction = {}
        )
    }
}

@Preview
@Composable
fun StackableItemPreview() {
    FalloutAppTheme {
        StackableItemPanel(
            stackable = StackableItem(ItemDataSource.getItemTemplateById(ItemDataSource.ID_CAPS), 1),
            count = 1,
            increaseButton = {},
            decreaseButton = {},
            buttonIcon = Res.drawable.enterprise_off_24dp,
            buttonAction = {}
        )
    }
}

@Preview
@Composable
fun ArmorDisplayPreview() {
    FalloutAppTheme {
        ArmorDisplay(Armor(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ARMOR_LEATHER), 0), Res.drawable.enterprise_off_24dp, {})
    }
}

@Preview
@Composable
fun ItemDisplayPreview() {
    FalloutAppTheme {
        ItemDisplay(
            item = BasicItem(ItemDataSource.getItemTemplateById(ItemDataSource.ID_BATTLE_STANDARD)),
            buttonIcon = Res.drawable.enterprise_off_24dp,
            buttonAction = {}
        )
    }
}