package com.example.shoppinglisttesting.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shoppinglisttesting.ui.screens.AddShoppingItemScreen
import com.example.shoppinglisttesting.ui.screens.HomeScreen
import com.example.shoppinglisttesting.ui.viewmodels.MainViewmodel
import com.example.shoppinglisttesting.ui.screens.ImageSelectionScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val mainViewmodel: MainViewmodel = hiltViewModel()

    NavHost(navController, startDestination = NavigationItem.Home.route) {

        composable(NavigationItem.Home.route) {
            HomeScreen(navController = navController, viewmodel = mainViewmodel)
        }
        navigation(startDestination = NavigationItem.Details.route, route = "shoppingGraph") {
            composable(NavigationItem.Details.route) {
                AddShoppingItemScreen(navController = navController, viewmodel = mainViewmodel)
            }
            composable(NavigationItem.ImageSelection.route) {
                ImageSelectionScreen(navController = navController, mainViewmodel = mainViewmodel)
            }
        }
    }
}