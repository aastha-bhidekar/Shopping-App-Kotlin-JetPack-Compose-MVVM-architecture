package com.example.shoppingapp.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shoppingapp.R
import com.example.shoppingapp.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Cart,
        Screen.Profile
    )

    BottomNavigation(
        backgroundColor = Color(0xFF121212),
        contentColor = Color.White,
        elevation = 8.dp
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = when (screen) {
                            Screen.Home -> R.drawable.ic_home
                            Screen.Categories -> R.drawable.ic_categories
                            Screen.Cart -> R.drawable.ic_cart
                            Screen.Profile -> R.drawable.ic_profile
                            else -> R.drawable.ic_default // Default icon
                        }),
                        contentDescription = screen.route
                    )
                },
                label = { Text(screen.route.capitalize()) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                selectedContentColor = Color(0xFF00B8D4),
                unselectedContentColor = Color(0xFFB0C4DE)
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
