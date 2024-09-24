package com.example.shoppingapp

import OrderNowScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.shoppingapp.navigation.CartScreen
import com.example.shoppingapp.screens.*
import com.example.shoppingapp.viewModel.AuthState
import com.example.shoppingapp.viewModel.AuthViewModel
import com.example.shoppingapp.viewModel.CartViewModel

@Composable
fun ShoppingAppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel // Shared CartViewModel
) {
    // Observe authentication state
    val authState = authViewModel.authState.observeAsState()

    NavHost(
        navController = navController,
        startDestination = getStartDestination(authState.value)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Cart.route) {
            CartScreen(cartViewModel = cartViewModel, navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screen.SignUp.route) {
            SignupScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(
            route = "categoryProducts/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            if (category != null) {
                CategoryProductsScreen(category = category, navController = navController)
            }
        }
        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null) {
                ProductDetailScreen(productId = productId, cartViewModel = cartViewModel)
            }
        }
        composable("order_now") { OrderNowScreen(cartViewModel,navController) }
    }

}

@Composable
fun getStartDestination(authState: AuthState?): String {
    Log.d("ShoppingAppNavHost", "AuthState: $authState")
    return when (authState) {
        is AuthState.Authenticated -> Screen.Home.route
        is AuthState.Unauthenticated -> Screen.Login.route
        else -> Screen.Login.route // Default route
    }
}
