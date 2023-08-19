package com.storeapp.lego.ui.screens.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storeapp.lego.domain.LegoUseCase
import com.storeapp.lego.domain.ShoppingCarUseCase
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.domain.model.ShoppingCarModel
import com.storeapp.lego.utils.ResState
import com.storeapp.lego.utils.ResponseListState
import com.storeapp.lego.utils.UiLisState
import com.storeapp.lego.utils.UiLisState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LegoViewModel @Inject constructor(
    private val legoUseCase: LegoUseCase,
    private val shoppingCarUseCase: ShoppingCarUseCase
) : ViewModel() {

    val uiState: StateFlow<UiLisState> = legoUseCase.flowLego().map(::Success)
        .catch { UiLisState.Error("Failed get lego list", it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(4000),
            UiLisState.Loading
        )

    val cart: Flow<List<ShoppingCarModel>> = shoppingCarUseCase.flowCart()

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading


    fun getProducts() {
        viewModelScope.launch {
            _loading.value = true
            when (val data = legoUseCase.getAll()) {
                is ResponseListState.Error -> _loading.value = false

                is ResponseListState.Success<LegoModel> -> {
                    if (data.data.isNotEmpty()) {
                        legoUseCase.addCart(data.data)
                    }
                    _loading.value = false
                }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        legoUseCase.logout()
        shoppingCarUseCase.deleteAll()
        legoUseCase.deleteAll()
    }

    fun addCart(legoModel: LegoModel) {
        viewModelScope.launch {
            shoppingCarUseCase.addToCar(legoModel)
        }
    }

    fun buyLegos(legos: List<ShoppingCarModel>) {
        viewModelScope.launch {
            _loading.value = true
            when(shoppingCarUseCase.buy()){
                is ResState.Error -> _loading.value = false
                is ResState.Success -> {
                    when(shoppingCarUseCase.updateLegos(legos)){
                        is ResState.Error -> _loading.value = false
                        is ResState.Success -> _loading.value = false
                    }
                }
            }
        }
    }
}