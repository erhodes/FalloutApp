package com.erhodes.falloutapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Header(text: String, modifier: Modifier = Modifier, secondaryText: String = "") {
    Row(
        modifier = modifier
            .padding(top = Dimens.paddingMedium, bottom = 3.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(all = Dimens.paddingMedium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            text = text,
            style = MaterialTheme.typography.titleLarge,
        )
        if (secondaryText.isNotEmpty()) {
            Text(
                text = secondaryText,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    FalloutAppTheme {
        Header(text = "Stats", secondaryText = "test")
    }
}