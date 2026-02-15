package com.erhodes.falloutapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.repository.ItemRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemViewModel: ViewModel(), KoinComponent {
    private val repo: ItemRepository by inject()

    var filterTier by mutableStateOf(0)
        private set

    fun updateFilterTier(tier: Int) {
        filterTier = tier
    }

    fun getAvailableItems(): Collection<ItemTemplate> {

        val items = if (filterTier > 0) {
            repo.getAllItemTemplates().filter { it.tier == filterTier }
        } else {
            repo.getAllItemTemplates()
        }

        return items.sortedBy { it.name }
    }
}