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
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainViewmodel: MainViewmodel = hiltViewModel()
    ) {

    NavHost(
        navController,
        startDestination = NavigationItem.Home.route
    ) {

        composable(NavigationItem.Home.route) {
            HomeScreen(
                navController = navController,
                viewmodel = mainViewmodel,
                onClickAdd = { route ->
                    navController.navigateSingleTopTo(route)
                }
            )
        }
        navigation(
            startDestination = NavigationItem.Details.route,
            route = "shoppingGraph"
        ) {
            composable(NavigationItem.Details.route) {
                AddShoppingItemScreen(
                    navController = navController,
                    viewmodel = mainViewmodel,
                    onNavigateToAddImage = { route ->
                        navController.navigateSingleTopTo(route)
                    }
                )
            }
            composable(NavigationItem.ImageSelection.route) {
                ImageSelectionScreen(
                    navController = navController,
                    mainViewmodel = mainViewmodel
                )
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

