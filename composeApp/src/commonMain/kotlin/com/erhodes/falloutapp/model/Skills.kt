package com.erhodes.falloutapp.model

import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.athletics
import falloutapp.composeapp.generated.resources.engineering
import falloutapp.composeapp.generated.resources.guns
import falloutapp.composeapp.generated.resources.medicine
import falloutapp.composeapp.generated.resources.melee
import falloutapp.composeapp.generated.resources.sabotage
import falloutapp.composeapp.generated.resources.science
import falloutapp.composeapp.generated.resources.sneak
import falloutapp.composeapp.generated.resources.social_sciences
import falloutapp.composeapp.generated.resources.speech
import falloutapp.composeapp.generated.resources.survival
import falloutapp.composeapp.generated.resources.throwing
import org.jetbrains.compose.resources.StringResource

enum class Skills(val description: StringResource) {
    ATHLETICS(Res.string.athletics),
    ENGINEERING(Res.string.engineering),
    GUNS(Res.string.guns),
    MEDICINE(Res.string.medicine),
    MELEE(Res.string.melee),
    SABOTAGE(Res.string.sabotage),
    SCIENCE(Res.string.science),
    SNEAK(Res.string.sneak),
    SOCIAL_SCIENCES(Res.string.social_sciences),
    SPEECH(Res.string.speech),
    SURVIVAL(Res.string.survival),
    THROWING(Res.string.throwing)
}