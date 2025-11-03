package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.Perk

class PerkRepository {

    companion object {
        val perkList: HashMap<Int, Perk> = HashMap<Int, Perk>()

        init {
            perkList[0] = Perk(0, "Power Armor Training", "You are able to properly use power armor")
            perkList[1] = Perk(1, "Hacker", "When hacking, you gain 1/3/5 guesses instead of 0/1/2")
        }

        fun getPerkById(id: Int): Perk {
            return perkList[id]!!
        }

        fun getAllPerks(): Collection<Perk> {
            return perkList.values
        }
    }

}