package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.repository.ItemRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemViewModel: ViewModel(), KoinComponent {
    private val repo: ItemRepository by inject()
    fun getAvailableItems(): Collection<ItemTemplate> {
        return repo.getAllItemTemplates()
    }
}