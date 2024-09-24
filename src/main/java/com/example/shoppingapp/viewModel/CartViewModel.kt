package com.example.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.dataClass.CartItem

class CartViewModel : ViewModel() {

    // LiveData to observe the cart items
    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    // Function to add item to the cart
    fun addItemToCart(item: CartItem) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()

        // Check if the item is already in the cart
        val existingItem = currentItems.find { it.id == item.id }

        if (existingItem != null) {
            // If item exists, increment the quantity
            existingItem.quantity++
        } else {
            // If item doesn't exist, add it as a new item
            currentItems.add(item)
        }

        // Update the LiveData to trigger observers
        _cartItems.value = currentItems

        // Debugging statement
        println("Cart size: ${_cartItems.value?.size}")
    }
    fun increaseQuantity(item: CartItem) {
        _cartItems.value = _cartItems.value?.map {
            if (it == item) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decreaseQuantity(item: CartItem) {
        _cartItems.value = _cartItems.value?.map {
            if (it == item && it.quantity > 1) it.copy(quantity = it.quantity - 1) else it
        }
    }
    // Function to calculate total price of the cart
    fun getTotalPrice(): Double {
        return _cartItems.value?.sumOf { it.price * it.quantity } ?: 0.0
    }

    fun clearCart() {
        _cartItems.value = emptyList() // Assuming you have cartItems as LiveData or MutableLiveData
    }
}
