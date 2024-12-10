package com.example.apizeldaparalelo

import HomeScreen
import TopBarComponent
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
import com.example.apizeldaparalelo.implementacionAPI.BossesViewModel
import com.example.apizeldaparalelo.implementacionAPI.CharacterViewModel
import com.example.apizeldaparalelo.implementacionAPI.DungeonViewModel
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel
import com.example.apizeldaparalelo.implementacionAPI.MonsterViewModel
import com.example.apizeldaparalelo.implementacionAPI.RetroFitConfig

@Composable
fun NavigationController(startDestination: String = "Home") {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBarComponent() },
        bottomBar = { BottomBarComponent(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable(route = "Home") {
                    HomeScreen()
                }
                composable(route = "Character") {
                    val api = RetroFitConfig.retrofit.create(ApiServices::class.java)
                    val characterViewModel: CharacterViewModel = remember { CharacterViewModel(api) }
                    val gameViewModel: GameViewModel = remember { GameViewModel(api) }
                    CharacterScreen(viewModel = characterViewModel, gameViewModel = gameViewModel)
                }
                composable(route = "Game") {
                    val api = RetroFitConfig.retrofit.create(ApiServices::class.java)
                    val gameViewModel: GameViewModel = remember { GameViewModel(api) }
                    GameScreen(viewModel = gameViewModel)
                }
                composable(route = "Monster") {
                    val api = RetroFitConfig.retrofit.create(ApiServices::class.java)
                    val monsterViewModel: MonsterViewModel = remember { MonsterViewModel(api) }
                    val gameViewModel: GameViewModel = remember { GameViewModel(api) }
                    MonsterScreen(viewModel = monsterViewModel, gameViewModel = gameViewModel)
                }
                composable(route = "Bosses") {
                    val api = RetroFitConfig.retrofit.create(ApiServices::class.java)
                    val bossesViewModel: BossesViewModel = remember { BossesViewModel(api) }
                    val gameViewModel: GameViewModel = remember { GameViewModel(api) }
                    BossScreen(viewModel = bossesViewModel, gameViewModel = gameViewModel)
                }
                composable(route = "Dungeons") {
                    val api = RetroFitConfig.retrofit.create(ApiServices::class.java)
                    val dungeonViewModel: DungeonViewModel = remember { DungeonViewModel(api) }
                    val gameViewModel: GameViewModel = remember { GameViewModel(api) }
                    DungeonScreen(viewModel = dungeonViewModel, gameViewModel = gameViewModel)
                }
            }
        }
    }
}
