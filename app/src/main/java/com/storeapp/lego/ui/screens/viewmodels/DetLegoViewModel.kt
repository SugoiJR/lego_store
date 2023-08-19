package com.storeapp.lego.ui.screens.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storeapp.lego.domain.DetailLegoUseCase
import com.storeapp.lego.domain.ShoppingCarUseCase
import com.storeapp.lego.domain.model.DetailLegoModel
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.domain.model.ShoppingCarModel
import com.storeapp.lego.utils.ResState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetLegoViewModel @Inject constructor(
    private val detailLegoUseCase: DetailLegoUseCase,
    private val shoppingCarUseCase: ShoppingCarUseCase
) : ViewModel() {

    fun lego(uid: String): Flow<LegoModel> = detailLegoUseCase.findLego(uid)

    val cart: Flow<List<ShoppingCarModel>> = shoppingCarUseCase.flowCart()

    private val _infoDetail: MutableLiveData<DetailLegoModel> = MutableLiveData()
    val infoDetail: LiveData<DetailLegoModel> = _infoDetail

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    fun getDetailLego(id: String) {
        viewModelScope.launch {
            _loading.value = true
            when (val info = detailLegoUseCase.getDetail(id)) {
                is ResState.Error -> _loading.value = false
                is ResState.Success -> {
                    _infoDetail.value = info.data
                    _loading.value = false
                }
            }
        }
    }

    fun addCart(legoModel: LegoModel) {
        viewModelScope.launch {
            shoppingCarUseCase.addToCar(legoModel)
        }
    }

    fun buyLegos(legos: List<ShoppingCarModel>) {
        viewModelScope.launch {
            _loading.value = true
            when (shoppingCarUseCase.buy()) {
                is ResState.Error -> _loading.value = false
                is ResState.Success -> {
                    when (shoppingCarUseCase.updateLegos(legos)) {
                        is ResState.Error -> _loading.value = false
                        is ResState.Success -> _loading.value = false
                    }
                }
            }
        }
    }
}