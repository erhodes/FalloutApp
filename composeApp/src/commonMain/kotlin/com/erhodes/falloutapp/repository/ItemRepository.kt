package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.ItemDataSource
import com.erhodes.falloutapp.model.ItemTemplate

class ItemRepository {

    val dataSource = ItemDataSource

    fun getAllItemTemplates(): Collection<ItemTemplate> {
        return dataSource.itemMap.values
    }
}