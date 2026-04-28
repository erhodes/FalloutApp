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
        traitMap[2] = Trait(
            name = "Positive Charge",
            description = "Lightning Arc increases in power with more allies within 3. 1: +5 range. 2: +1 dice. 3: +1 arc. 4: +1 damage",
            id = 2,
        )
        traitMap[3] = Trait(
            name = "Swarm Tactics",
            description = "As a reaction to another ghoul ending its turn, take your turn (if you are ready). Swarm Tactics cannot be used in reaction to a turn granted by Swarm Tactics.",
            id = 3,
        )
        traitMap[4] = Trait(
            name = "Coordinated Assault",
            description = "Gain an additional die when attacking an enemy that is engaged by another ally.",
            id = 4,
        )
        traitMap[5] = Trait(
            name = "Inflexible Programming",
            description = "After finishing a free turn, the next turn is a fixed turn in which this char must take the same actions.",
            id = 5,
        )
        traitMap[6] = Trait(
            name = "Leaping Charge",
            description = "Roll a second dice on melee attacks made as part of a charge.",
            id = 6,
        )
        traitMap[7] = Trait(
            name = "Evasion",
            description = "As a reaction, gain Defence 2 against a single attack this creature is aware of.",
            id = 7,
        )
        traitMap[8] = Trait(
            name = "Stealthy",
            description = "Roll an additional dice on Hide checks.",
            id = 8,
        )
        traitMap[9] = Trait(
            name = "Assassin",
            description = "Roll an additional dice when attacking creatures this creature is hidden from.",
            id = 9,
        )
     }
}
