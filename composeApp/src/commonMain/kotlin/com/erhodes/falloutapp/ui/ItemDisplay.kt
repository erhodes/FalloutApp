package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.Armor
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate

@Composable
fun ItemDisplay(item: Item, buttonLabel: String, buttonAction: () -> Unit) {
    Row {
        Text(item.name)
        Button(
            onClick = buttonAction
        ) {
            Text(buttonLabel)
        }
    }
}

@Composable
fun ItemTemplateDisplay(template: ItemTemplate, buttonLabel: String, buttonAction: () -> Unit) {
    Row {
        Text(template.name)
        Button(
            onClick = buttonAction
        ) {
            Text(buttonLabel)
        }
    }
}

@Composable
fun ArmorDisplay(armor: Armor, buttonLabel: String, buttonAction: () -> Unit) {
    Row {
        Column {
            Text(armor.name)
            Text("Durability ${armor.durability} Toughness ${armor.toughness}")
        }

        Button(
            onClick = buttonAction
        ) {
            Text(buttonLabel)
        }
    }
}