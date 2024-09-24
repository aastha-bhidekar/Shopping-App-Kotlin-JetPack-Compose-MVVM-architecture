package com.example.shoppingapp.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush

@Composable
fun CategoryCard(categoryName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        backgroundColor = Color.Transparent,
        elevation = 4.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF0F2027), Color(0xFF2C5364)) // Dark blue to black gradient
                    )
                )
        ) {
            Text(
                text = categoryName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
