package com.example.shoppingapp.List

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.cards.CategoryCard

@Composable
fun CategoriesRow(
    categories: List<String>,
    isLoading: Boolean,
    errorMessage: String?,
    onCategoryClick: (String) -> Unit
) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 16.dp)
            )
        }
    } else if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 16.dp)
            )
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                CategoryCard(categoryName = category) {
                    onCategoryClick(category)
                }
            }
        }
    }
}

