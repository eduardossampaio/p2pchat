package com.apps.esampaio.p2pchat.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apps.esampaio.p2pchat.core.model.Chat

// Screen.kt
sealed class Screen(val route: String) {
    data object Profile: Screen("profile_screen")
    data object ChatList: Screen("chat_list_screen")
    data object ChatScreen: Screen("chat_screen")
}

@Composable
fun App() {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(navController = navController, startDestination = Screen.Profile.route) {
            composable(route = Screen.Profile.route) {
                ProfileScreen() { _ ->
                    navController.navigate(Screen.ChatList.route)
                }
            }
            composable(route = Screen.ChatList.route) {
                ChatListScreen() { chat ->
                    navController.navigate(Screen.ChatScreen.route)
                }
            }
            composable(route = Screen.ChatScreen.route) {
                ChatScreen(
                    Chat(1, "Aline", "Olá, tudo bem?", "https://i.pravatar.cc/150?img=1"),
                    onNavigateBack = { navController.popBackStack() }
                )

            }
        }
    }
}