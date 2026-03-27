package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.model.condition.Burning
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.model.condition.Immobilized
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ManageConditionsScreen(
    onAddCondition: (Condition) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.paddingMedium)
    ) {
        Button(onClick = { onAddCondition(Burning(1)) }) {
            Text("Burning")
        }
        Button(onClick = { onAddCondition(Immobilized(1)) }) {
            Text("Immobilized")
        }
    }
}

@Preview
@Composable
fun ManageConditionsScreenPreview() {
    FalloutAppTheme {
        ManageConditionsScreen(
            onAddCondition = {}
        )
    }
}