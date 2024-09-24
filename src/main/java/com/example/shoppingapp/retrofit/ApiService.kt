package com.example.shoppingapp.retrofit

import com.example.shoppingapp.dataClass.Product
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

}

