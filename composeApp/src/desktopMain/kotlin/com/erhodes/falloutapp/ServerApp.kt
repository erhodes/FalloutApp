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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erhodes.falloutapp.presentation.CharacterViewModel
import com.erhodes.falloutapp.presentation.EncounterViewModel
import com.erhodes.falloutapp.presentation.UserViewModel
import com.erhodes.falloutapp.ui.AddEnemyScreen
import com.erhodes.falloutapp.ui.EncounterScreen
import com.erhodes.falloutapp.ui.UserListScreen
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.add_enemy
import falloutapp.composeapp.generated.resources.back_button
import falloutapp.composeapp.generated.resources.encounters
import falloutapp.composeapp.generated.resources.user_list
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

// For server only screens
enum class ServerScreen(val title: StringResource) {
    UserListScreen(title = Res.string.user_list),
    EncounterScreen(title = Res.string.encounters),
    AddEnemyScreen(title = Res.string.add_enemy)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerApp(
    userViewModel: UserViewModel = viewModel { UserViewModel() },
    characterViewModel: CharacterViewModel = viewModel { CharacterViewModel() },
    encounterViewModel: EncounterViewModel = viewModel { EncounterViewModel() },
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
            startDestination = ServerScreen.EncounterScreen.name,
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
                val encounter by encounterViewModel.encounter.collectAsState()
                EncounterScreen(
                    encounter = encounter!!,
                    onAddEnemyClicked = {
                        navController.navigate(ServerScreen.AddEnemyScreen.name)
                    }
                )
            }
            composable(route = ServerScreen.AddEnemyScreen.name) {
                AddEnemyScreen(
                    onEnemySelected = {
                        encounterViewModel.onAddEnemy(it)
                        navController.navigate(ServerScreen.EncounterScreen.name)
                    }
                )
            }
        }
    }
}
