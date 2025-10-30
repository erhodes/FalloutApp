package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.repository.ItemRepository

class ItemViewModel: ViewModel() {
    private val repo = ItemRepository()

    fun getAvailableItems(): List<ItemTemplate> {
        return repo.availableItems
    }
}