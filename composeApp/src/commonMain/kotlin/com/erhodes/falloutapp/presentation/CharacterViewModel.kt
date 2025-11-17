package com.erhodes.falloutapp.presentation

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.model.Item
import com.erhodes.falloutapp.model.ItemTemplate
import com.erhodes.falloutapp.model.Perk
import com.erhodes.falloutapp.model.Weapon
import com.erhodes.falloutapp.repository.CharacterRepository
import com.erhodes.falloutapp.repository.PerkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
): ViewModel(), KoinComponent {
    private val repo: CharacterRepository by inject()

    private val perkRepository: PerkRepository by inject()

    val characters = repo.characters

    private var activeCharacter = Character("empty")
    private val _activeCharacterState = MutableStateFlow(CharacterUiState(activeCharacter))
    val activeCharacterState = _activeCharacterState.asStateFlow()

    private var gainSkillsState = GainSkillUiState(activeCharacter, 1, true)
    private val _gainSkillsUiState = MutableStateFlow(gainSkillsState)
    val gainSkillsUiState = _gainSkillsUiState.asStateFlow()

    //TODO add DI and move this logic to the creation view model

    fun setActiveCharacter(character: Character) {
        activeCharacter = character
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
        }

    }

    fun onDeleteCharacterClicked(character: Character) {
        repo.removeCharacter(character)
    }

    private fun updateBonusSkillsState(newState: GainSkillUiState) {
        gainSkillsState = newState
        scope.launch {
            _gainSkillsUiState.update { gainSkillsState }
        }
    }

    fun resetBonusSkillsState(bonuses: Int, milestone: Boolean) {
        updateBonusSkillsState(GainSkillUiState(activeCharacter, bonuses, milestone))
    }

    fun onIncreaseSkillClicked(ordinal: Int) {
        if (activeCharacter.skills[ordinal] + 1 > activeCharacter.getMaxSkillValue()
            || gainSkillsState.bonuses <= 0) return
        gainSkillsState.appliedBonuses[ordinal]++
        updateBonusSkillsState(gainSkillsState.copy(bonuses = gainSkillsState.bonuses-1))
    }

    fun onDecreaseSkillClicked(ordinal: Int) {
        if (gainSkillsState.appliedBonuses[ordinal] <= 0) return
        gainSkillsState.appliedBonuses[ordinal]--
        updateBonusSkillsState(gainSkillsState.copy(bonuses = gainSkillsState.bonuses+1))
    }

    fun onIncreaseSkillsFinalized() {
        repo.increaseSkillsForCharacter(gainSkillsState.appliedBonuses, activeCharacter, gainSkillsState.milestone)
    }

    fun onPerkSelected(perk: Perk) {
        repo.addPerkToCharacter(perk, activeCharacter)
    }

    fun onRemovePerk(perk: Perk) {
        repo.removePerkFromCharacter(perk, activeCharacter)
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
        }
    }

    fun increaseStackCountForActiveCharacter(item: Item, count: Int) {
        if (item is Weapon) return addAmmoToWeapon(item, count)
        repo.increaseStackCountForCharacter(item, count, activeCharacter)
        updateActiveCharacter()
    }

    fun decreaseStackCountForActiveCharacter(item: Item, count: Int) {
        if (item is Weapon) return removeAmmoFromWeapon(item, count)
        repo.decreaseStackCountForCharacter(item, count, activeCharacter)
        updateActiveCharacter()
    }

    fun addAmmoToWeapon(weapon: Weapon, count: Int) {
        repo.addAmmoToWeapon(weapon, count)
        updateActiveCharacter()
    }

    fun removeAmmoFromWeapon(weapon: Weapon, count: Int) {
        repo.removeAmmoFromWeapon(weapon, count)
        updateActiveCharacter()
    }

    fun addNewItemToActiveCharacter(newItem: ItemTemplate) {
        repo.addNewItemToCharacter(newItem, activeCharacter)
        scope.launch {
            _activeCharacterState.update{ CharacterUiState(activeCharacter) }
        }
    }

    fun removeItemFromActiveCharacter(item: Item) {
        repo.removeItemFromCharacter(item, activeCharacter)
        updateActiveCharacter()
    }

    fun equipItemToCharacter(item: Item) {
        repo.equipItemToCharacter(item, activeCharacter)
        updateActiveCharacter()
    }

    fun unequipItemFromCharacter(item: Item) {
        repo.unequipItemFromCharacter(item, activeCharacter)
        updateActiveCharacter()
    }

    private fun updateActiveCharacter() {
        scope.launch {
            _activeCharacterState.update { CharacterUiState(activeCharacter) }
        }
    }

    fun onDamageCharacterClicked(amount: Int) {
        repo.damageCharacter(amount, activeCharacter)
        updateActiveCharacter()
    }

    fun onHealCharacterClicked(amount: Int) {
        repo.healCharacter(amount, activeCharacter)
        updateActiveCharacter()
    }

    fun onRepairArmorClicked(amount: Int) {
        repo.repairArmorForCharacter(amount, activeCharacter)
        updateActiveCharacter()
    }

    fun onModifyStressClicked(amount: Int) {
        repo.modifyStressForCharacter(amount, activeCharacter)
        updateActiveCharacter()
    }

    fun onModifyFatigueClicked(amount: Int) {
        repo.modifyFatigueForCharacter(amount, activeCharacter)
        updateActiveCharacter()
    }

    fun onModifyRadiationClicked(amount: Int) {
        repo.modifyRadiationForCharacter(amount, activeCharacter)
        updateActiveCharacter()
    }

    fun getAllPerks(): Collection<Perk> {
        return perkRepository.getAllPerks()
    }
}