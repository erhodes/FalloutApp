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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.BasicItem
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.StackableItem
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GenericItemDisplay(
    title: String,
    summary: String,
    buttonLabel: String,
    buttonAction: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryButtonLabel: String? = null,
    secondaryButtonAction: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 5.dp)
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
                )
                Text(
                    text = summary
                )
            }
            Spacer(Modifier.weight(0.5f))
            Button(
                onClick = buttonAction,
            ) {
                Text(buttonLabel)
            }
            if (secondaryButtonLabel != null && secondaryButtonAction != null) {
                Button(
                    onClick = secondaryButtonAction,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(secondaryButtonLabel)
                }
            }
        }


        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.paddingSmall),
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
    buttonLabel: String,
    buttonAction: () -> Unit,
    secondaryButtonLabel: String? = null,
    secondaryButtonAction: (() -> Unit)? = null
) {
    GenericItemDisplay(
        title = item.name,
        summary = "Load ${item.load}",
        buttonLabel = buttonLabel,
        buttonAction = buttonAction,
        secondaryButtonLabel = secondaryButtonLabel,
        secondaryButtonAction = secondaryButtonAction
    ) {
        Text(
            text = item.description
        )
    }
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = item.name,
//            style = MaterialTheme.typography.displaySmall
//        )
//        Text(
//            text = "Load: ${item.load}"
//        )
//        Row(
//            horizontalArrangement = Arrangement.End,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = item.description,
//                modifier = Modifier.weight(0.5f)
//                    .padding(end = 10.dp)
//            )
//            Button(
//                onClick = buttonAction,
//            ) {
//                Text(buttonLabel)
//            }
//        }
//    }
}

@Composable
fun ItemTemplateDisplay(template: ItemTemplate, buttonLabel: String, buttonAction: () -> Unit) {
    GenericItemDisplay(
        title = template.name,
        summary = "Load: ${template.load}",
        buttonLabel = buttonLabel,
        buttonAction = buttonAction
    ) {
        Text(template.description)
    }

//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = template.name,
//            style = MaterialTheme.typography.displaySmall
//        )
//        Text(
//            text = "Load: ${template.load}"
//        )
//        Row(
//            horizontalArrangement = Arrangement.End,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = template.description,
//                modifier = Modifier.weight(0.5f)
//                    .padding(end = 10.dp)
//            )
//            Button(
//                onClick = buttonAction,
//            ) {
//                Text(buttonLabel)
//            }
//        }
//    }
}

@Composable
fun ArmorDisplay(
    armor: Armor,
    buttonLabel: String,
    buttonAction: () -> Unit,
    secondaryButtonLabel: String? = null,
    secondaryButtonAction: (() -> Unit)? = null
) {
    GenericItemDisplay(
        title = armor.name,
        summary = "Load: ${armor.load}  Durability: ${armor.damageTaken}/${armor.durability} Toughness:${armor.toughness}",
        buttonLabel = buttonLabel,
        buttonAction = buttonAction,
        secondaryButtonLabel = secondaryButtonLabel,
        secondaryButtonAction = secondaryButtonAction
    ) {
        Text(armor.description)
    }
}

@Composable
fun WeaponPanel(
    weapon: Weapon,
    ammo: Int,
    increaseButton: () -> Unit,
    decreaseButton: () -> Unit,
    buttonLabel: String,
    buttonAction: () -> Unit,
    secondaryButtonLabel: String? = null,
    secondaryButtonAction: (() -> Unit)? = null
) {
    GenericItemDisplay(
        title = weapon.name,
        summary = "Load: ${weapon.load}",
        buttonLabel = buttonLabel,
        buttonAction = buttonAction,
        secondaryButtonLabel = secondaryButtonLabel,
        secondaryButtonAction = secondaryButtonAction
    ) {
        Column {
            Text(weapon.description)
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
                            Text(weapon.ability[i], Modifier.weight(spacing))
                        }
                    }
                }
                if (weapon.magazineSize > 0) {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Ammo $ammo/${weapon.magazineSize}"
                    )
                    Button(
                        onClick = increaseButton
                    ) {
                        Text("+")
                    }
                    Button(
                        onClick = decreaseButton
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
    increaseButton: () -> Unit,
    decreaseButton: () -> Unit,
    buttonLabel: String,
    buttonAction: () -> Unit,
    secondaryButtonLabel: String? = null,
    secondaryButtonAction: (() -> Unit)? = null
) {
    GenericItemDisplay(
        title = stackable.name,
        summary = "Up to ${stackable.max} per load. Current Load: ${stackable.load} ",
        buttonLabel = buttonLabel,
        buttonAction = buttonAction,
        secondaryButtonLabel = secondaryButtonLabel,
        secondaryButtonAction = secondaryButtonAction
    ) {
        Column {
            Text(stackable.description)
            Row {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Quantity $count"
                )
                Button(
                    onClick = increaseButton
                ) {
                    Text("+")
                }
                Button(
                    onClick = decreaseButton
                ) {
                    Text("-")
                }
            }
        }
    }
}

@Preview(widthDp = 700)
@Composable
fun WeaponDisplayPreview() {
    FalloutAppTheme {
        WeaponPanel(
            Weapon(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ASSAULT_RIFLE), 0),
            0,  {}, {}, "Equip", {},
            secondaryButtonLabel = "Discard",
            secondaryButtonAction = {})
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
            buttonLabel = "Equip",
            buttonAction = {}
        )
    }
}

@Preview
@Composable
fun ArmorDisplayPreview() {
    FalloutAppTheme {
        ArmorDisplay(Armor(ItemDataSource.getItemTemplateById(ItemDataSource.ID_ARMOR_LEATHER), 0), "Equip", {})
    }
}

@Preview
@Composable
fun ItemDisplayPreview() {
    FalloutAppTheme {
        ItemDisplay(BasicItem(ItemDataSource.getItemTemplateById(ItemDataSource.ID_BATTLE_STANDARD)), "Equip", {})
    }
}