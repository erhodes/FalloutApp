package com.erhodes.falloutapp.data

import com.erhodes.falloutapp.model.Trait

object TraitDataSource {
    val traitMap: HashMap<Int, Trait> = HashMap()
    fun getTraitById(id: Int): Trait {
        return traitMap[id]!!
    }
    init {
        traitMap[0] = Trait(
            name = "Immobile",
            description = "Cannot move.",
            id = 0,
        )
        traitMap[1] = Trait(
            name = "Get Him!",
            description = "After attacking an enemy, allies roll 1 more dice when attacking it until the end of this character's next turn",
            id = 1,
        )
     }
}
