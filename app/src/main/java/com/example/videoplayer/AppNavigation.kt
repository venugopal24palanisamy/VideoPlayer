package com.example.videoplayer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {

    val navHostController = rememberNavController()

    NavHost(navController = navHostController,
        startDestination = "home_screen"){

        composable("home_screen"){
            HomeScreen(navHostController)
        }

        composable("about_screen"){
            AboutScreen(navHostController)
        }
    }
}