package com.erhodes.falloutapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.erhodes.falloutapp.presentation.UserViewModel
import com.erhodes.falloutapp.ui.UserListScreen
import falloutapp.composeapp.generated.resources.Res
import falloutapp.composeapp.generated.resources.user_list
import org.jetbrains.compose.resources.StringResource

// For server only screens
enum class ServerScreen(val title: StringResource) {
    UserListScreen(title = Res.string.user_list)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerApp(
    userViewModel: UserViewModel = viewModel { UserViewModel() },
    characterViewModel: CharacterViewModel = viewModel { CharacterViewModel() },
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fallout Server") }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ServerScreen.UserListScreen.name,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = ServerScreen.UserListScreen.name) {
                val users by userViewModel.users.collectAsState()
                val groups by userViewModel.userCharacterGroups.collectAsState()
                UserListScreen(
                    users = users,
                    groups = groups
                )
            }
        }
    }
}
