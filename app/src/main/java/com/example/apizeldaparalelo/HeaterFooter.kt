package com.example.apizeldaparalelo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color




@Composable
fun BottomBarComponent(navController: NavController) {
    BottomAppBar(
        containerColor = Color.Black,  // Fondo de la barra de navegación en negro
        contentColor = Color.White,  // Iconos y texto en blanco
        content = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Character")
                    }) {
                        // Usar icono descargado para personajes
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.brutal),
                            contentDescription = "Character",
                            modifier = Modifier.size(24.dp) // Tamaño del icono
                        )
                    }
                    Text(text = "Character")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Game")
                    }) {
                        // Usar icono descargado para juegos
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.gamepad),
                            contentDescription = "Game",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(text = "Game")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Bosses")
                    }) {
                        // Usar icono descargado para jefes
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.boss),
                            contentDescription = "Bosses",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(text = "Bosses")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Dungeons")
                    }) {
                        // Usar icono descargado para mazmorras
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.dungeons),
                            contentDescription = "Dungeons",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(text = "Dungeons")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Monster")
                    }) {
                        // Usar icono descargado para monstruos
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.monster),
                            contentDescription = "Monster",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(text = "Monster")
                }
            }
        }
    )
}