package com.example.android_advance.ui.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_advance.domain.usecases.GetProductUseCase
import com.example.android_advance.utils.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
public class ProductViewModel @Inject public constructor(private val getProductUseCase: GetProductUseCase) :
    ViewModel() {
    private val _state = mutableStateOf(ProductState())
    val state: State<ProductState> = _state

    init {
        getListProducts()
    }

    private fun getListProducts() {
        getProductUseCase().onEach { it ->
            when (it) {
                is Resource.Success -> {
                    _state.value = ProductState(listProduct = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = ProductState(error = it.message ?: "An unexpected error occured")
                }

                is Resource.Loading -> {
                    _state.value = ProductState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}