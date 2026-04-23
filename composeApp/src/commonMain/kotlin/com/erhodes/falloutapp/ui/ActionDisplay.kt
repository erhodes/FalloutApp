package com.erhodes.falloutapp.ui

import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.action.Action
import org.jetbrains.compose.resources.stringResource

@Composable
fun actionDescription(action: Action, testValue: Int): String {
    if (!action.isTested()) return action.effectText
    val statLetter = stringResource(action.stat!!.letter)
    val skillName = stringResource(action.skill!!.description)
    return "$statLetter + $skillName ($testValue). ${action.effectText}"
}
