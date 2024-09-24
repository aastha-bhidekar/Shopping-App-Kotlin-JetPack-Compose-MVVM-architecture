package com.example.shoppingapp.repository

import com.example.shoppingapp.dataClass.Product
import com.example.shoppingapp.retrofit.ApiService
import com.example.shoppingapp.retrofit.RetrofitInstance

class ProductRepository() {

    private val apiService = RetrofitInstance.apiService

    suspend fun getProducts(): List<Product> {
        return try {
            // Fetch products from the API
            apiService.getProducts()
        } catch (e: Exception) {
            // Handle exceptions
            emptyList()
        }
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        return apiService.getProductsByCategory(category)
    }

    suspend fun getProductById(productId: Int): Product {
        return apiService.getProductById(productId)
    }


}
