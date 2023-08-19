package com.storeapp.lego.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.storeapp.lego.data.repository.LoginFirebaseRepo
import com.storeapp.lego.domain.model.RegisterModel
import javax.inject.Inject

class LoginFirebaseUseCase @Inject constructor(
    private val loginFirebaseRepo: LoginFirebaseRepo
) {
    fun signIn(email: String, password: String): Task<AuthResult> {
        return loginFirebaseRepo.signIn(email, password)
    }

    fun register(registerModel: RegisterModel): Task<AuthResult> {
        return loginFirebaseRepo.register(registerModel)
    }
}