package com.erhodes.falloutapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erhodes.falloutapp.presentation.CampaignViewModel
import com.erhodes.falloutapp.presentation.CharacterViewModel
import com.erhodes.falloutapp.presentation.EncounterViewModel
import com.erhodes.falloutapp.presentation.ItemViewModel
import com.erhodes.falloutapp.presentation.LocationViewModel
import com.erhodes.falloutapp.presentation.SettlementUiState
import com.erhodes.falloutapp.presentation.UserViewModel
import com.erhodes.falloutapp.ui.AddEnemyScreen
import com.erhodes.falloutapp.ui.CampaignManagementScreen
import com.erhodes.falloutapp.ui.EncounterListScreen
import com.erhodes.falloutapp.ui.EncounterScreen
import com.erhodes.falloutapp.ui.LocationListScreen
import com.erhodes.falloutapp.ui.LocationScreen
import com.erhodes.falloutapp.ui.SettlementScreen
import com.erhodes.falloutapp.ui.StoreScreen
import com.erhodes.falloutapp.ui.UserListScreen
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.add_enemy
import falloutapp.composeapp.generated.resources.back_button
import falloutapp.composeapp.generated.resources.campaign
import falloutapp.composeapp.generated.resources.encounter_list
import falloutapp.composeapp.generated.resources.encounters
import falloutapp.composeapp.generated.resources.location
import falloutapp.composeapp.generated.resources.locations
import falloutapp.composeapp.generated.resources.store
import falloutapp.composeapp.generated.resources.user_list
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

// For server only screens
enum class ServerScreen(val title: StringResource) {
    UserListScreen(title = Res.string.user_list),
    EncounterScreen(title = Res.string.encounters),
    AddEnemyScreen(title = Res.string.add_enemy),
    CampaignScreen(title = Res.string.campaign),
    EncounterListScreen(title = Res.string.encounter_list),
    LocationListScreen(title = Res.string.locations),
    LocationScreen(title = Res.string.location),
    StoreScreen(title = Res.string.store)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerApp(
    userViewModel: UserViewModel = viewModel { UserViewModel() },
    characterViewModel: CharacterViewModel = viewModel { CharacterViewModel() },
    encounterViewModel: EncounterViewModel = viewModel { EncounterViewModel() },
    locationViewModel: LocationViewModel = viewModel { LocationViewModel() },
    campaignViewModel: CampaignViewModel = viewModel { CampaignViewModel() },
    itemViewModel: ItemViewModel = viewModel { ItemViewModel() },
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fallout Server") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ServerScreen.CampaignScreen.name,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = ServerScreen.UserListScreen.name) {
                val users by userViewModel.users.collectAsState()
                val groups by userViewModel.userCharacterGroups.collectAsState()
                UserListScreen(
                    users = users,
                    groups = groups,
                    onDeleteClicked = { characterViewModel.onDeleteCharacterClicked(it) }
                )
            }
            composable(route = ServerScreen.EncounterScreen.name) {
                val encounterState by encounterViewModel.activeEncounterState.collectAsState()
                EncounterScreen(
                    state = encounterState,
                    onAddEnemyClicked = {
                        navController.navigate(ServerScreen.AddEnemyScreen.name)
                    },
                    onTakeDamage = { index, amount -> encounterViewModel.onTakeDamage(index, amount) },
                    onHealDamage = { index, amount -> encounterViewModel.onHealDamage(index, amount) },
                    onRepair = { index, amount -> encounterViewModel.onRepairArmor(index, amount) },
                    onRemoveEnemy = { index -> encounterViewModel.onRemoveEnemy(index) },
                    onRenameEnemy = { index, newName -> encounterViewModel.onRenameEnemy(index, newName) },
                    onRenameEncounter = { newName -> encounterViewModel.onRenameEncounter(newName) },
                    onSaveClicked = { encounterViewModel.onSaveEncounter() },
                )
            }
            composable(route = ServerScreen.AddEnemyScreen.name) {
                AddEnemyScreen(
                    onEnemySelected = {
                        encounterViewModel.onAddEnemy(it)
                        navController.popBackStack()
                    }
                )
            }
            composable(route = ServerScreen.CampaignScreen.name) {
                val campaignState by campaignViewModel.activeCampaignState.collectAsState()
                CampaignManagementScreen(
                    campaignState,
                    onEncountersClicked = { navController.navigate(ServerScreen.EncounterListScreen.name) },
                    onLocationsClicked = { navController.navigate(ServerScreen.LocationListScreen.name) }
                )
            }
            composable(route = ServerScreen.EncounterListScreen.name) {
                val encounters by encounterViewModel.savedEncounters.collectAsState()
                LaunchedEffect(Unit) { encounterViewModel.loadSavedEncounters() }
                EncounterListScreen(
                    encounters = encounters,
                    onSelect = { encounter ->
                        encounterViewModel.onSelectEncounter(encounter)
                        navController.navigate(ServerScreen.EncounterScreen.name)
                    },
                    onNewEncounter = {
                        encounterViewModel.onNewEncounter()
                        navController.navigate(ServerScreen.EncounterScreen.name)
                    }
                )
            }
            composable(route = ServerScreen.LocationListScreen.name) {
                val locations by locationViewModel.savedLocations.collectAsState()
                val activeLocationState by locationViewModel.activeLocationState.collectAsState()
                LaunchedEffect(Unit) { locationViewModel.loadSavedLocations() }
                LocationListScreen(
                    locations = locations,
                    activeLocationId = activeLocationState.id,
                    onSelect = { location ->
                        locationViewModel.onSelectLocation(location)
                        navController.navigate(ServerScreen.LocationScreen.name)
                    },
                    onSetActive = { location -> locationViewModel.onSetActiveLocation(location) },
                    onNewLocation = {
                        locationViewModel.onNewLocation()
                        navController.navigate(ServerScreen.LocationScreen.name)
                    }
                )
            }
            composable(route = ServerScreen.LocationScreen.name) {
                val locationState by locationViewModel.activeLocationState.collectAsState()
                val state = locationState
                if (state is SettlementUiState) {
                    SettlementScreen(
                        state = state,
                        onEditLocation = { name, description ->
                            locationViewModel.onEditLocation(name, description)
                        },
                        onAddFeature = { type, name, description ->
                            locationViewModel.onAddFeature(type, name, description)
                        },
                        onRemoveFeature = { id -> locationViewModel.onRemoveFeature(id) },
                        onOpenStore = { id ->
                            locationViewModel.onOpenStore(id)
                            navController.navigate(ServerScreen.StoreScreen.name)
                        }
                    )
                } else {
                    LocationScreen(
                        state = state,
                        onEditLocation = { name, description ->
                            locationViewModel.onEditLocation(name, description)
                        },
                        onAddFeature = { type, name, description ->
                            locationViewModel.onAddFeature(type, name, description)
                        },
                        onRemoveFeature = { id -> locationViewModel.onRemoveFeature(id) },
                        onOpenStore = { id ->
                            locationViewModel.onOpenStore(id)
                            navController.navigate(ServerScreen.StoreScreen.name)
                        }
                    )
                }
            }
            composable(route = ServerScreen.StoreScreen.name) {
                val storeState by locationViewModel.activeStoreState.collectAsState()
                val buyers by locationViewModel.buyers.collectAsState()
                val message by locationViewModel.purchaseMessage.collectAsState()
                val state = storeState
                // The store can go away under the back stack - deleted from the location screen, or
                // a different location selected - so this route has to cope with nothing to show.
                if (state == null) {
                    Text("No store selected.")
                } else {
                    StoreScreen(
                        state = state,
                        buyers = buyers,
                        templates = itemViewModel.getAvailableItems(),
                        message = message,
                        onDismissMessage = { locationViewModel.onDismissPurchaseMessage() },
                        onBuy = { entryIndex, buyerIndex ->
                            locationViewModel.onBuy(entryIndex, buyerIndex)
                        },
                        onAddStock = { templateId, quantity, price ->
                            locationViewModel.onAddStock(templateId, quantity, price)
                        },
                        onEditStock = { entryIndex, quantity, price ->
                            locationViewModel.onEditStock(entryIndex, quantity, price)
                        },
                        onRemoveStock = { entryIndex ->
                            locationViewModel.onRemoveStock(entryIndex)
                        }
                    )
                }
            }
        }
    }
}
