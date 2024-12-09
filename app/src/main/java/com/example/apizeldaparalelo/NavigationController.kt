package com.example.apizeldaparalelo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apizeldaparalelo.implementacionAPI.ApiServices
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel
import com.example.apizeldaparalelo.implementacionAPI.RetroFitConfig


@Composable
fun NavigationController(startDestination: String = "Character") {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBarComponent() },
        bottomBar = { BottomBarComponent(navController) },
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable(route = "Character") {
                    Character()
                }
                composable(route = "Game") { backStackEntry ->
                    // Usar NavBackStackEntry para obtener el ViewModel
                    val api = RetroFitConfig.retrofit.create(ApiServices::class.java)
                    val viewModel: GameViewModel = remember { GameViewModel(api) }

                    // Llamar a la pantalla con el ViewModel
                    GameScreen(viewModel = viewModel)
                }
                composable(route = "Bosses") {
                    Bosses()
                }
                composable(route = "Dungeons") {
                    Dungeons()
                }
                composable(route = "Monster") {
                    Monster()
                }
            }
        }
    }
}





