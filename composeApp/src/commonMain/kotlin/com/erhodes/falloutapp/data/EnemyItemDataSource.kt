package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.WeaponTemplate
import com.erhodes.falloutapp.model.ability.Ability
import com.erhodes.falloutapp.model.ability.AbilityGroup
import com.erhodes.falloutapp.model.ability.Arc
import com.erhodes.falloutapp.model.ability.Burst
import com.erhodes.falloutapp.model.ability.Frighten
import com.erhodes.falloutapp.model.ability.Maim
import com.erhodes.falloutapp.model.ability.NoAbility
import com.erhodes.falloutapp.model.ability.Radiation

/**
 * This data source contains items that are not accessible to players, such as a creature's natural weapons.
 */
object EnemyItemDataSource {
    // Ids start at 9000 so they never collide with ItemDataSource: items are serialized by id alone,
    // and ItemTemplateSerializer checks ItemDataSource first.
    const val ID_LIGHTNING_ARC = 9000
    const val ID_RADIOACTIVE_FISTS = 9001
    const val ID_RAZOR_SHARP_CLAWS = 9002

    val itemMap = HashMap<Int, ItemTemplate>()
    fun getItemTemplateById(id: Int): ItemTemplate {
        return itemMap[id]!!
    }

    init {
        // weapons
        itemMap[ID_LIGHTNING_ARC] = WeaponTemplate(
            name = "Lightning Arc",
            description = "A blast of lightning.",
            load = 0,
            tier = 1,
            id = ID_LIGHTNING_ARC,
            damage = listOf(1, 2, 3),
            ability = listOf(Arc(1), Arc(2), Arc(3)),
            passive = listOf(),
            range = 15,
            magazineSize = 0
        )
        itemMap[ID_RADIOACTIVE_FISTS] = WeaponTemplate(
            name = "Radioactive Fists",
            description = "",
            load = 0,
            tier = 1,
            id = ID_RADIOACTIVE_FISTS,
            damage = listOf(1, 2, 3),
            ability = listOf(
                AbilityGroup(listOf(Radiation(2), Frighten(1))),
                AbilityGroup(listOf(Radiation(3), Frighten(1))),
                AbilityGroup(listOf(Radiation(4), Frighten(2))),
            ),
            passive = listOf<Ability>(),
            range = 1,
            magazineSize = 0
        )
        itemMap[ID_RAZOR_SHARP_CLAWS] = WeaponTemplate(
            name = "Razor Sharp Claws",
            description = "",
            load = 0,
            tier = 1,
            id = ID_RAZOR_SHARP_CLAWS,
            damage = listOf(1, 2, 3),
            ability = listOf(Maim(1), Maim(2), Maim(3)),
            passive = listOf<Ability>(),
            range = 1,
            magazineSize = 0
        )
    }
}