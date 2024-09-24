package com.example.shoppingapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppingapp.repository.CategoryRepository

class CategoriesViewModel : ViewModel() {

    private val repository = CategoryRepository()

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> get() = _selectedCategory

    // Simulate loading categories (you should replace this with actual data fetching logic)
    suspend fun loadCategories() {
        _isLoading.value = true
        // Assume this is fetched from an API or database
        val catergory = repository.getCategories()
        _categories.value = catergory
        _isLoading.value = false
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }
}
