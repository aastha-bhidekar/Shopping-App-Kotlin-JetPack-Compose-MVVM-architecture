package com.example.shoppingapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.dataClass.Product
import com.example.shoppingapp.repository.ProductRepository
import kotlinx.coroutines.launch


class ProductsViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val productList = repository.getProducts()
                _products.value = productList
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load products: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val productsList = repository.getProductsByCategory(category)
                _products.value = productsList
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load products"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
