package com.example.shoppingapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppingapp.viewModel.ProductDetailViewModel
import com.example.shoppingapp.viewModel.CartViewModel
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow
import com.example.shoppingapp.dataClass.CartItem

@Composable
fun ProductDetailScreen(
    productId: Int,
    productDetailViewModel: ProductDetailViewModel = viewModel(),
    cartViewModel: CartViewModel // Passing shared CartViewModel
) {
    val product = productDetailViewModel.product.observeAsState(null)
    val isLoading = productDetailViewModel.isLoading.observeAsState(false)
    val errorMessage = productDetailViewModel.errorMessage.observeAsState(null)

    val scaffoldState = rememberScaffoldState()
    var showSnackbar by remember { mutableStateOf(false) }

    LaunchedEffect(productId) {
        productDetailViewModel.loadProductDetails(productId)
    }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            scaffoldState.snackbarHostState.showSnackbar("Item added to cart")
            showSnackbar = false
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
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
                product.value?.let { it ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(it.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(300.dp)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        val cartItem = CartItem(
                                            id = it.id,
                                            productName = it.title,
                                            imageUrl = it.image,
                                            quantity = 1,
                                            price = it.price
                                        )
                                        cartViewModel.addItemToCart(cartItem)
                                        showSnackbar = true
                                    },
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text("Add to Cart")
                                }
                            }
                            var expanded by remember { mutableStateOf(false) }
                            val description = if (expanded) it.description else it.description.take(100)

                            Text(
                                text = description,
                                style = MaterialTheme.typography.body1,
                                color = Color.Gray,
                                maxLines = if (expanded) Int.MAX_VALUE else 3,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (it.description.length > 100) {
                                ClickableText(
                                    text = AnnotatedString(if (expanded) "Show less" else "Read more"),
                                    style = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                                    onClick = { expanded = !expanded }
                                )
                            }
                            Text(
                                text = "Price: $${it.price}",
                                style = MaterialTheme.typography.h6,
                                color = Color(0xFF388E3C),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Rating: ${it.rating.rate} ★ (${it.rating.count} reviews)",
                                style = MaterialTheme.typography.body2,
                                color = Color(0xFFFFD700),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
