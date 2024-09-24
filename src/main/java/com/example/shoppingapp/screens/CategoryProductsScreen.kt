package com.example.shoppingapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppingapp.viewModel.ProductsViewModel
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import com.example.shoppingapp.cards.ProductCard

@Composable
fun CategoryProductsScreen(
    category: String,
    navController: NavHostController,
    productsViewModel: ProductsViewModel = viewModel()
) {
    val products = productsViewModel.products.observeAsState(emptyList())
    val isLoading = productsViewModel.isLoading.observeAsState(false)
    val errorMessage = productsViewModel.errorMessage.observeAsState(null)

    LaunchedEffect(category) {
        productsViewModel.loadProductsByCategory(category)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F2027), Color(0xFF2C5364)) // Dark blue to black gradient
                )
            )
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (errorMessage.value != null) {
            Text(
                text = errorMessage.value ?: "Unknown error",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products.value) { product ->
                    ProductCard(
                        product = product
                    ) {
                        navController.navigate("productDetail/${product.id}")
                    }
                }
            }
        }
    }
}
