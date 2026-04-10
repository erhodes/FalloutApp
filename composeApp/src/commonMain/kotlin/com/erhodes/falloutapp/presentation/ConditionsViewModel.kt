package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import com.erhodes.falloutapp.model.condition.ConditionTemplate
import com.erhodes.falloutapp.repository.CharacterRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConditionsViewModel : ViewModel(), KoinComponent {
    val characterRepository: CharacterRepository by inject()

    fun getManageableConditions(): List<ConditionTemplate> {
        val result = ArrayList<ConditionTemplate>()
        result.add(ConditionTemplate.Burning)
        result.add(ConditionTemplate.Immobilized)
        result.add(ConditionTemplate.Shredded)

        return result
    }
}
