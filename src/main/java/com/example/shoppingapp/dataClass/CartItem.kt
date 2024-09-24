package com.example.shoppingapp.dataClass

data class CartItem(
    val id: Int,
    val productName: String,
    val imageUrl: String,
    var quantity: Int = 1,
    val price: Double
)
