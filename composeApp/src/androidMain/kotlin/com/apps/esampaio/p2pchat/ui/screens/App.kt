package com.apps.esampaio.p2pchat.ui.screens

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Screen.kt
sealed class Screen(val route: String) {
    data object Profile: Screen("profile_screen")
    data object ChatList: Screen("chat_list_screen")
    data object ChatScreen: Screen("chat_screen")
}

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Profile.route) {
        composable(route = Screen.Profile.route) {
            ProfileScreen(){ _ ->
                navController.navigate(Screen.ChatList.route)
            }
        }
        composable(route = Screen.ChatList.route) {
            ChatListScreen(){ chat ->
                navController.navigate(Screen.ChatScreen.route)
            }
        }
        composable(route = Screen.ChatScreen.route) {
            ChatScreen(
                "fulano",
                "192.168.18.0"
            ){

            }
        }
    }

}