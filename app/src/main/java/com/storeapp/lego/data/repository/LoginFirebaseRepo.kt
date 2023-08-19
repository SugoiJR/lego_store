package com.storeapp.lego.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.storeapp.lego.domain.model.RegisterModel
import javax.inject.Inject


class LoginFirebaseRepo @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
) {
    fun signIn(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    fun register(registerModel: RegisterModel): Task<AuthResult> {
        val dbReference = firebaseDatabase.getReference("Users")

        return firebaseAuth.createUserWithEmailAndPassword(
            registerModel.email, registerModel.password
        ).addOnCompleteListener {

            val user = firebaseAuth.currentUser!!
            user.sendEmailVerification()

            val currentUserDb = dbReference.child(user.uid)
            currentUserDb.child("firstName").setValue(registerModel.firstName)
            currentUserDb.child("lastName").setValue(registerModel.lastName)
            Log.d("TAG-REGISTERED", "true")
        }.addOnFailureListener {
            Log.d("TAG-REGISTERED", "false - $it")
        }
    }
}