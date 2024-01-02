package com.starorigins.crypstocks.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.starorigins.crypstocks.data.repositories.stocks.IEXService
import com.starorigins.crypstocks.data.repositories.stocks.StocksRepository
import com.starorigins.crypstocks.ui.screens.home.HomeScreen
import com.starorigins.crypstocks.ui.screens.home.HomeViewModel
import com.starorigins.crypstocks.ui.screens.userauth.LoginScreen
import com.starorigins.crypstocks.ui.screens.userauth.RegisterScreen

@Composable
fun TestApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "register") {
        composable(route = "register") {
            RegisterScreen(navController = navController)
        }
        composable(route = "login") {
            LoginScreen(navController = navController)
        }
        composable(route = "home") {
            HomeScreen(viewModel = viewModel(),navController = navController)
        }
    }
}