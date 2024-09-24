package com.example.shoppingapp.repository

import com.example.shoppingapp.dataClass.Product
import com.example.shoppingapp.retrofit.RetrofitInstance

class CategoryRepository {

    private val apiService = RetrofitInstance.apiService

    suspend fun getCategories(): List<String> {
        return apiService.getCategories()
    }
}