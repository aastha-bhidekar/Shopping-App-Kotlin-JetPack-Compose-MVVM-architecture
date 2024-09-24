package com.example.shoppingapp.List

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.cards.ProductCard
import com.example.shoppingapp.dataClass.Product


@Composable
fun ProductList(
    products: List<Product>,
    PisLoading: Boolean,
    PerrorMessage: String?,
    onProductClick: (Product) -> Unit
){
    if (PisLoading) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 16.dp)
            )
        }
    } else if (PerrorMessage != null) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = PerrorMessage,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 16.dp)
            )
        }
    } else {
        Column {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 435.dp)  // Constrain height to avoid infinite height issues
            ) {
                items(products) { product ->
                    ProductCard(product = product, onClick = onProductClick)
                }
            }
        }
    }
}
