package com.erhodes.falloutapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.erhodes.falloutapp.presentation.CharacterCreationViewModel
import com.erhodes.falloutapp.presentation.CharacterViewModel
import com.erhodes.falloutapp.presentation.ItemViewModel
import com.erhodes.falloutapp.presentation.LoginStateViewModel
import com.erhodes.falloutapp.ui.*
import falloutapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class FalloutScreen(val title: StringResource) {
    CharacterList(title = Res.string.character_list),
    CharacterCreation(title = Res.string.character_creation),
    CharacterScreen(title = Res.string.character_screen),
    AddItemScreen(title = Res.string.acquire_item),
    BonusSkillsScreen(title = Res.string.bonus_skills),
    PerkSelectScreen(title = Res.string.select_perk),
    Login(title = Res.string.login)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FalloutAppBar(
    currentScreen: FalloutScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun FalloutApp(
    viewModel: CharacterViewModel = viewModel { CharacterViewModel() },
    itemViewModel: ItemViewModel = viewModel { ItemViewModel() },
    creationViewModel: CharacterCreationViewModel = viewModel { CharacterCreationViewModel() },
    loginStateViewModel: LoginStateViewModel = viewModel { LoginStateViewModel() },
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = FalloutScreen.entries.firstOrNull { it.name == backStackEntry?.destination?.route } ?: FalloutScreen.CharacterScreen

    Scaffold(
        topBar = {
            FalloutAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FalloutScreen.CharacterList.name,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = FalloutScreen.CharacterList.name) {
                val characterList = remember { viewModel.characters }
                CharacterListScreen(
                    characterList,
                    onSelect = {
                        viewModel.setActiveCharacter(it)
                        navController.navigate(FalloutScreen.CharacterScreen.name)
                    },
                    onDeleteClicked = { viewModel.onDeleteCharacterClicked(it) },
                    onNewCharacter = {
                        creationViewModel.startNewCreation()
                        navController.navigate(FalloutScreen.CharacterCreation.name)
                    },
                    onLogin = { navController.navigate(FalloutScreen.Login.name) }
                )
            }
            composable(route = FalloutScreen.CharacterCreation.name) {
                val uiState by creationViewModel.creationUiState.collectAsState()
                CharacterCreationScreen(
                    uiState = uiState,
                    onIncrement = { creationViewModel.incrementStat(it) },
                    onDecrement = { creationViewModel.decrementStat(it) },
                    onMajorClicked = { creationViewModel.onMajorClicked(it) },
                    onMinorClicked = { creationViewModel.onMinorClicked(it) },
                    onComplete = {
                        val newChar = creationViewModel.addCharacter(it, uiState)
                        viewModel.setActiveCharacter(newChar)
                        viewModel.resetBonusSkillsState(newChar.intelligence, false)
                        navController.navigate(FalloutScreen.BonusSkillsScreen.name)
                    }
                )
            }
            composable(route = FalloutScreen.CharacterScreen.name) {
                val uiState by viewModel.activeCharacterState.collectAsState()

                CharacterScreen(
                    state = uiState,
                    onTakeDamage = { viewModel.onDamageCharacterClicked(it) },
                    onHealDamage = { viewModel.onHealCharacterClicked(it) },
                    onRepair = { viewModel.onRepairArmorClicked(it) },
                    onModifyStress = { viewModel.onModifyStressClicked(it) },
                    onModifyFatigue = { viewModel.onModifyFatigueClicked(it) },
                    onModifyRadiation = { viewModel.onModifyRadiationClicked(it) },
                    onGainMilestone = {
                        viewModel.resetBonusSkillsState(1, true)
                        navController.navigate(FalloutScreen.BonusSkillsScreen.name)
                    },
                    onAddPerk = { navController.navigate(FalloutScreen.PerkSelectScreen.name) },
                    onRemovePerk = { viewModel.onRemovePerk(it) },
                    onEquipItem = { viewModel.equipItemToCharacter(it) },
                    onUnequipItem = { viewModel.unequipItemFromCharacter(it) },
                    onDiscardItem = { viewModel.removeItemFromActiveCharacter(it) },
                    onIncreaseItem = { viewModel.increaseStackCountForActiveCharacter(it, 1) },
                    onDecreaseItem = { viewModel.decreaseStackCountForActiveCharacter(it, 1) },
                    onAddItem = {
                        navController.navigate(FalloutScreen.AddItemScreen.name)
                    }
                )
            }
            composable(route = FalloutScreen.AddItemScreen.name) {
                AcquireItemScreen(
                    items = itemViewModel.getAvailableItems(),
                    onAcquireItem = {
                        viewModel.addNewItemToActiveCharacter(it)
                        navController.popBackStack()
                    }
                )
            }
            composable(route = FalloutScreen.BonusSkillsScreen.name) {
                val uiState by viewModel.gainSkillsUiState.collectAsState()
                GainSkillsScreen(
                    uiState = uiState,
                    onIncreaseClicked = { viewModel.onIncreaseSkillClicked(it) },
                    onDecreaseClicked = { viewModel.onDecreaseSkillClicked(it) },
                    onFinalizeClicked = {
                        viewModel.onIncreaseSkillsFinalized()
                        if (uiState.milestone) {
                            navController.popBackStack()
                        } else {
                            navController.popBackStack(route = FalloutScreen.CharacterList.name, false)
                        }
                    }
                )
            }
            composable(route = FalloutScreen.PerkSelectScreen.name) {
                PerkSelectScreen(
                    state = viewModel.activeCharacterState.value,
                    perks = viewModel.getAllPerks(),
                    onSelect = {
                        viewModel.onPerkSelected(it)
                        navController.popBackStack()
                    }
                )
            }
            composable(route = FalloutScreen.Login.name) {
                LoginScreen(
                    onLogin = { name, address ->
                        loginStateViewModel.login(name, address)
                    },
                    onSync = {
                        loginStateViewModel.sync()
                    }
                )
            }
        }
    }
}
