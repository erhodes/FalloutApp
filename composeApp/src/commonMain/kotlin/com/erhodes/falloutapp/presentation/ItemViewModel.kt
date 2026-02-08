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

    var currentTier by mutableStateOf(0)
        private set

    fun selectCurrentTier(tier: Int) {
        currentTier = tier
    }

    fun getAvailableItems(): Collection<ItemTemplate> {

        val items = if (currentTier > 0) {
            repo.getAllItemTemplates().filter { it.tier == currentTier }
        } else {
            repo.getAllItemTemplates()
        }

        return items.sortedBy { it.name }
    }
}