package com.example.shoppingapp.retrofit

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitInstance {

    private const val BASE_URL = "https://fakestoreapi.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
