package com.example.shoppingapp.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.dataClass.Product
import androidx.compose.ui.graphics.Brush

@Composable
fun ProductCard(product: Product, onClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(product) },
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = product.image),
                    contentDescription = product.title,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = product.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                    Text(
                        text = "$${product.price}",
                        color = Color(0xFF388E3C), // Price color from ProductDetailScreen
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Rating: ${product.rating.rate} (${product.rating.count} reviews)",
                        color = Color(0xFFFFD700), // Rating color from ProductDetailScreen
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
