package com.example.shoppingapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shoppingapp.R
import com.example.shoppingapp.ShoppingAppNavHost
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.navigation.BottomNavigationBar
import com.example.shoppingapp.viewModel.AuthState
import com.example.shoppingapp.viewModel.AuthViewModel
import com.example.shoppingapp.viewModel.CartViewModel

@Composable
fun ShoppingApp(navController: NavHostController, authViewModel: AuthViewModel) {

    val authState = authViewModel.authState.observeAsState()
    val cartViewModel: CartViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (authState.value is AuthState.Authenticated) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF0F2027), Color(0xFF2C5364))
                    )
                )
        ) {
            ShoppingAppNavHost(navController, authViewModel, cartViewModel)
        }
    }
}
