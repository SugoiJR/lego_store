package com.storeapp.lego.ui.screens.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storeapp.lego.domain.LoginFirebaseUseCase
import com.storeapp.lego.domain.model.RegisterModel
import com.storeapp.lego.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginFirebaseUseCase: LoginFirebaseUseCase
) : ViewModel() {

    private val _onLogin: MutableLiveData<UIState> = MutableLiveData()
    val onLogin: LiveData<UIState> = _onLogin

    private val _onRegister: MutableLiveData<UIState> = MutableLiveData()
    val onRegister: LiveData<UIState> = _onRegister

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            _onLogin.value = UIState.Loading
            loginFirebaseUseCase.signIn(email, password).addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> _onLogin.value = UIState.Success(it.isSuccessful)
                    false -> _onLogin.value = UIState.Error(
                        title = "authentication failed",
                        message = it.exception?.message
                    )
                }
            }
        }
    }

    fun doRegister(registerModel: RegisterModel) {
        viewModelScope.launch {
            _onRegister.value = UIState.Loading
            loginFirebaseUseCase.register(registerModel).addOnCompleteListener {
                if (it.isSuccessful){
                    _onRegister.value = UIState.Success(true)
                } else {
                    _onRegister.value = UIState.Error(
                        title = "Register failed",
                        message = it.exception?.message
                    )
                }
            }.addOnFailureListener {
                _onRegister.value = UIState.Error(
                    title = "Register failed",
                    message = it.message
                )
            }
        }
    }
}