package com.example.shoppingapp.screens

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.R
import com.example.shoppingapp.viewModel.CategoriesViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.viewModel.ProductsViewModel
import com.example.shoppingapp.List.CategoriesRow
import com.example.shoppingapp.List.ProductList

@Composable
fun HomeScreen(navController: NavController) {
    val proViewModel: ProductsViewModel = viewModel()
    val catViewModel: CategoriesViewModel = viewModel()

    val categories = catViewModel.categories.observeAsState(emptyList())
    val products = proViewModel.products.observeAsState(emptyList())

    val isLoading = catViewModel.isLoading.observeAsState(false)
    val errorMessage = catViewModel.errorMessage.observeAsState(null)

    LaunchedEffect(Unit) {
        catViewModel.loadCategories()
        proViewModel.loadProducts()
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0F2027), Color(0xFF2C5364)) // Dark blue to black gradient
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                SearchBar()
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                CategoriesRow(categories.value, isLoading.value, errorMessage.value) { category ->
                    navController.navigate("categoryProducts/${category}")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Products",
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                ProductList(products.value, false, null) { product ->
                    navController.navigate("productDetail/${product.id}")
                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search for products...", color = Color.White.copy(alpha = 0.7f)) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .background(Color.White.copy(alpha = 0.2f)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = Color.White
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon",
                tint = Color.White
            )
        }
    )
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}
