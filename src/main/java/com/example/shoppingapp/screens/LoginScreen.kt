package com.example.shoppingapp.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.viewModel.AuthState
import com.example.shoppingapp.viewModel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel ) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isEmailValid = remember { mutableStateOf(true) }
    val isPasswordValid = remember { mutableStateOf(true) }
    val context = LocalContext.current
    val isChecked = remember { mutableStateOf(false) }
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error ).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }
    Surface(color = Color.White) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.Login_Title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Email Section
        Text(
            text = stringResource(R.string.email),
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            label = { Text(text = "example@gmail.com") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon"
                )
            },
            shape = RoundedCornerShape(16.dp)
        )
        if (!isEmailValid.value) {
            Text(
                text = stringResource(R.string.invalid_email),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password Section
        Text(
            text = stringResource(R.string.password),
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                isPasswordValid.value = it.length >= 8
            },
            label = { Text(text = "*********") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Password Icon"
                )
            },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        if (!isPasswordValid.value) {
            Text(
                text = stringResource(R.string.password_length),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Remember me checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = it }
            )
            Text(text = "remember_me", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign in Button
        Button(
            onClick = {
                if (isEmailValid.value && isPasswordValid.value &&
                    email.value.isNotEmpty() && password.value.isNotEmpty() && isChecked.value) {
                    authViewModel.login(email.value, password.value)
                } else {
                    if (email.value.isEmpty()) {
                        Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show()
                    }
                    if (password.value.isEmpty()) {
                        Toast.makeText(context, "Password is empty", Toast.LENGTH_SHORT).show()
                    }
                    if (!isChecked.value) {
                        Toast.makeText(context, "Please accept the terms", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Sign In")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Sign In Icon")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up and Forgot Password Links
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = stringResource(R.string.account))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign Up",
                modifier = Modifier.clickable { navController.navigate("signup") },
                color = Color.Blue
            )
        }

        Text(
            text = "Forgot Password",
            modifier = Modifier
                .clickable { /* Navigate to Forgot Password */ }
                .padding(top = 8.dp),
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Divider
        Divider(color = Color.Gray, thickness = 2.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // Facebook Login Button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(3.dp, Color.Blue), RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook Image",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.facebook_login), color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Google Login Button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(3.dp, Color.Blue), RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Image",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.google_login), color = Color.Black)
            }
        }
    }}
}
