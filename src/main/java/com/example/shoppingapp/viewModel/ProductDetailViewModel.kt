package com.example.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.dataClass.Product
import com.example.shoppingapp.repository.ProductRepository
import com.example.shoppingapp.retrofit.ApiService
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productRepository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> get() = _product

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadProductDetails(productId: Int) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                // Fetch product details using the repository
                val response = productRepository.getProductById(productId)
                _product.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
