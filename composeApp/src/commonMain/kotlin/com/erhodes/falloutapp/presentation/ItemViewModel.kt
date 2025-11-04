package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.repository.ItemRepository

class ItemViewModel: ViewModel() {

    fun getAvailableItems(): Collection<ItemTemplate> {
        return ItemRepository.itemList.values
    }
}