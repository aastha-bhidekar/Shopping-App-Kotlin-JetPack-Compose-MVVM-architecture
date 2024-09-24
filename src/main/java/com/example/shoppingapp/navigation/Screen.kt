package com.example.shoppingapp

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object SignUp : Screen("signUp")
    object Login : Screen("login")
}
