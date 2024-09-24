package com.example.shoppingapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.viewModel.CartViewModel
import com.example.shoppingapp.dataClass.CartItem
import coil.compose.rememberImagePainter

@Composable
fun CartScreen(cartViewModel: CartViewModel, navController: NavController) {
    val cartItems = cartViewModel.cartItems.observeAsState(emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Cart",
            style = MaterialTheme.typography.h4,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp) // Add some space below the heading
        )
        Spacer(modifier = Modifier.width(20.dp))
        if (cartItems.value.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems.value) { item: CartItem ->
                    CartItemCard(item,cartViewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total: $${cartViewModel.getTotalPrice()}",
                style = MaterialTheme.typography.h5,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Button(
                onClick = {
                    navController.navigate("order_now") // Navigate to Order Now screen
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buy Now")
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Your cart is empty", style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Composable
fun CartItemCard(item: CartItem, cartViewModel: CartViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.productName,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Price: $${item.price}",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Quantity :",
                        style = MaterialTheme.typography.body2,
                        color = Color.Black
                    )

                    // Decrease quantity button
                    IconButton(
                        onClick = {
                            if (item.quantity > 1) {
                                cartViewModel.decreaseQuantity(item)
                            }
                        }
                    ) {
                        Text("-",
                            style = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Bold))
                    }

                    Text(
                        text = "${item.quantity}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // Increase quantity button
                    IconButton(
                        onClick = { cartViewModel.increaseQuantity(item) }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
                    }
                }
            }
        }
    }
}
