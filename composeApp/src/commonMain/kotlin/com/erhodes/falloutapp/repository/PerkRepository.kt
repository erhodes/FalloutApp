package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.PerkDataSource
import com.erhodes.falloutapp.model.Perk

class PerkRepository {

    val dataSource = PerkDataSource

    fun getAllPerks(): Collection<Perk> {
        return dataSource.perkMap.values
    }
}