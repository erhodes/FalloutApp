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
    val itemMap = HashMap<Int, ItemTemplate>()
    fun getItemTemplateById(id: Int): ItemTemplate {
        return itemMap[id]!!
    }

    init {
        // weapons
        itemMap[0] = WeaponTemplate(
            name = "Lightning Arc",
            description = "A blast of lightning.",
            load = 0,
            tier = 1,
            id = 0,
            damage = listOf(1, 2, 3),
            ability = listOf(Arc(1), Arc(2), Arc(3)),
            passive = listOf(),
            range = 15,
            magazineSize = 0
        )
        itemMap[1] = WeaponTemplate(
            name = "Radioactive Fists",
            description = "",
            load = 0,
            tier = 1,
            id = 1,
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
        itemMap[2] = WeaponTemplate(
            name = "Razor Sharp Claws",
            description = "",
            load = 0,
            tier = 1,
            id = 2,
            damage = listOf(1, 2, 3),
            ability = listOf(Maim(1), Maim(2), Maim(3)),
            passive = listOf<Ability>(),
            range = 1,
            magazineSize = 0
        )
    }
}