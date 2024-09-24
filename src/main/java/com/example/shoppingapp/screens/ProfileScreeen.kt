package com.example.shoppingapp.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppingapp.Screen
import com.example.shoppingapp.viewModel.AuthState
import com.example.shoppingapp.viewModel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    val username = authViewModel.username.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(Screen.Login.route)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F2027), Color(0xFF2C5364)) // Dark blue to black gradient
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Profile Picture Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.2f), shape = CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = username.value?.firstOrNull()?.toString() ?: "U", // First letter as placeholder
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Username Text
            Text(
                text = username.value ?: "Guest User", // Default message if username is null
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Welcome to ShoppingApp",
                style = MaterialTheme.typography.body1,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Sign Out Button
            Button(
                onClick = {
                    authViewModel.signout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red) // Red for signout
            ) {
                Text(
                    "Sign Out",
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )
            }
        }
    }
}
