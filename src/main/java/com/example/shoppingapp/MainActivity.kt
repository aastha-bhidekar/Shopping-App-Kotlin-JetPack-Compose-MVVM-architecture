package com.example.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.screens.ShoppingApp
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewModel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            ShoppingAppTheme {
                val navController = rememberNavController()
                ShoppingApp(navController = navController, authViewModel = authViewModel )
            }
        }
    }
}


