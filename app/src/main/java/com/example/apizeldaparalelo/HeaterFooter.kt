package com.example.apizeldaparalelo


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(){
    TopAppBar({ Text(stringResource(R.string.app_name))})
}

@Composable
fun BottomBarComponent(navController: NavController) {
    BottomAppBar(
        content = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Character")
                    }) {
                        Icon(imageVector = Icons.Default.Face, contentDescription = "Character")
                    }
                    Text(text = "Character")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Game")
                    }) {
                        Icon(imageVector = Icons.Default.Build, contentDescription = "Game")
                    }
                    Text(text = "Game")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Bosses")
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Bosses")
                    }
                    Text(text = "Bosses")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Dungeons")
                    }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Dungeons")
                    }
                    Text(text = "Dungeons")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        navController.navigate("Monster")
                    }) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = "Monster")
                    }
                    Text(text = "Monster")
                }
            }
        }
    )
}