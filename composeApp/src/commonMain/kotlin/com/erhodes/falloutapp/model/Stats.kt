package com.erhodes.falloutapp.model

import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.a
import falloutapp.composeapp.generated.resources.agility
import falloutapp.composeapp.generated.resources.c
import falloutapp.composeapp.generated.resources.charisma
import falloutapp.composeapp.generated.resources.e
import falloutapp.composeapp.generated.resources.endurance
import falloutapp.composeapp.generated.resources.i
import falloutapp.composeapp.generated.resources.intelligence
import falloutapp.composeapp.generated.resources.l
import falloutapp.composeapp.generated.resources.luck
import falloutapp.composeapp.generated.resources.p
import falloutapp.composeapp.generated.resources.perception
import falloutapp.composeapp.generated.resources.s
import falloutapp.composeapp.generated.resources.strength
import org.jetbrains.compose.resources.StringResource

enum class Stats(val displayName: StringResource, val letter: StringResource) {
    STRENGTH(Res.string.strength, Res.string.s),
    PERCEPTION(Res.string.perception, Res.string.p),
    ENDURANCE(Res.string.endurance, Res.string.e),
    CHARISMA(Res.string.charisma, Res.string.c),
    INTELLIGENCE(Res.string.intelligence, Res.string.i),
    AGILITY(Res.string.agility, Res.string.a),
    LUCK(Res.string.luck, Res.string.l)
}