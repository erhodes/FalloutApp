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
    characterViewModel: CharacterViewModel = viewModel { CharacterViewModel() },
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
                val characterList = remember { characterViewModel.characters }
                val remoteCharacterList = remember { characterViewModel.remoteCharacters }
                CharacterListScreen(
                    characters = characterList,
                    remoteCharacters = remoteCharacterList,
                    onSelect = {
                        characterViewModel.setActiveCharacter(it)
                        navController.navigate(FalloutScreen.CharacterScreen.name)
                    },
                    onSelectRemote = {
                        characterViewModel.setActiveCharacter(it)
                        navController.navigate(FalloutScreen.CharacterScreen.name)
                    },
                    onDeleteClicked = { characterViewModel.onDeleteCharacterClicked(it) },
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
                        characterViewModel.setActiveCharacter(newChar)
                        characterViewModel.resetBonusSkillsState(newChar.intelligence, false)
                        navController.navigate(FalloutScreen.BonusSkillsScreen.name)
                    }
                )
            }
            composable(route = FalloutScreen.CharacterScreen.name) {
                val uiState by characterViewModel.activeCharacterState.collectAsState()

                CharacterScreen(
                    state = uiState,
                    onTakeDamage = { characterViewModel.onDamageCharacterClicked(it) },
                    onHealDamage = { characterViewModel.onHealCharacterClicked(it) },
                    onRepair = { characterViewModel.onRepairArmorClicked(it) },
                    onModifyStress = { characterViewModel.onModifyStressClicked(it) },
                    onModifyFatigue = { characterViewModel.onModifyFatigueClicked(it) },
                    onModifyRadiation = { characterViewModel.onModifyRadiationClicked(it) },
                    onGainMilestone = {
                        characterViewModel.resetBonusSkillsState(1, true)
                        navController.navigate(FalloutScreen.BonusSkillsScreen.name)
                    },
                    onAddPerk = { navController.navigate(FalloutScreen.PerkSelectScreen.name) },
                    onRemovePerk = { characterViewModel.onRemovePerk(it) },
                    onEquipItem = { characterViewModel.equipItemToCharacter(it) },
                    onUnequipItem = { characterViewModel.unequipItemFromCharacter(it) },
                    onDiscardItem = { characterViewModel.removeItemFromActiveCharacter(it) },
                    onIncreaseItem = { item, count -> characterViewModel.increaseStackCountForActiveCharacter(item, count) },
                    onDecreaseItem = { item, count -> characterViewModel.decreaseStackCountForActiveCharacter(item, count) },
                    onAddItem = {
                        navController.navigate(FalloutScreen.AddItemScreen.name)
                    }
                )
            }
            composable(route = FalloutScreen.AddItemScreen.name) {
                AcquireItemScreen(
                    items = itemViewModel.getAvailableItems(),
                    selectedTier = itemViewModel.currentTier,
                    onTierChanged = { itemViewModel.setCurrentTier(it) },
                    onAcquireItem = {
                        characterViewModel.addNewItemToActiveCharacter(it)
                        navController.popBackStack()
                    }
                )
            }
            composable(route = FalloutScreen.BonusSkillsScreen.name) {
                val uiState by characterViewModel.gainSkillsUiState.collectAsState()
                GainSkillsScreen(
                    uiState = uiState,
                    onIncreaseClicked = { characterViewModel.onIncreaseSkillClicked(it) },
                    onDecreaseClicked = { characterViewModel.onDecreaseSkillClicked(it) },
                    onFinalizeClicked = {
                        characterViewModel.onIncreaseSkillsFinalized()
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
                    state = characterViewModel.activeCharacterState.value,
                    perks = characterViewModel.getAllPerks(),
                    onSelect = {
                        characterViewModel.onPerkSelected(it)
                        navController.popBackStack()
                    }
                )
            }
            composable(route = FalloutScreen.Login.name) {
                val loginState = loginStateViewModel.loginState.collectAsState()
                LoginScreen(
                    loggedIn = loginState.value,
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
