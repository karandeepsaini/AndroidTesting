package com.example.shoppinglisttesting.ui


enum class Screen{
    Home,
    Detail,
    ImageSelection
}

sealed class NavigationItem(val route : String){
    object Home : NavigationItem(Screen.Home.name)
    object Details : NavigationItem(Screen.Detail.name)
    object ImageSelection : NavigationItem(Screen.ImageSelection.name)
}