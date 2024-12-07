package com.example.apizeldaparalelo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



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
                composable(route = "Game") {
                    Game()
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

