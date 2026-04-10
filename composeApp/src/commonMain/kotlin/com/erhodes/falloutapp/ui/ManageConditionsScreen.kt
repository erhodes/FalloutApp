package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erhodes.falloutapp.model.condition.ConditionTemplate
import com.erhodes.falloutapp.model.condition.Condition
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ManageConditionsScreen(
    conditionTemplates: List<ConditionTemplate>,
    onAddCondition: (Condition) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.paddingMedium)
    ) {
        conditionTemplates.forEach {
            Button(onClick = { onAddCondition(Condition.buildNewCondition(it)) }) {
                Text(it.title)
            }
        }
    }
}

@Preview
@Composable
fun ManageConditionsScreenPreview() {
    FalloutAppTheme {
        ManageConditionsScreen(
            conditionTemplates = listOf(ConditionTemplate.Burning, ConditionTemplate.Immobilized),
            onAddCondition = {}
        )
    }
}