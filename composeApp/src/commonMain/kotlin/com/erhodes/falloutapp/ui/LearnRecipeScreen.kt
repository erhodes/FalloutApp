package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.Recipe
import com.erhodes.falloutapp.presentation.CharacterUiState
import com.erhodes.falloutapp.ui.theme.Dimens
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.learn
import org.jetbrains.compose.resources.stringResource

@Composable
fun LearnRecipeScreen(
    state: CharacterUiState,
    recipes: Collection<Recipe>,
    onSelect: (Recipe) -> Unit,
) {
    val character = state.character
    Column {
        recipes.forEach { recipe ->
            RecipePanel(
                recipe = recipe,
                buttonLabel = stringResource(Res.string.learn),
                buttonEnabled = character.canLearnRecipe(recipe),
                onClick = { onSelect(recipe) },
                modifier = Modifier.padding(horizontal = Dimens.paddingSmall, vertical = 2.dp)
            )
        }
    }
}
