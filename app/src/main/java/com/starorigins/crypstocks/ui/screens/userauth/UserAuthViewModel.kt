package com.starorigins.crypstocks.ui.screens.userauth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class UserAuthViewModel: ViewModel() {
    private var userAuthUIState = mutableStateOf(UserAuthUIState())
    private val TAG = UserAuthViewModel::class.simpleName

    fun onEvent(event:UserAuthEvents) {
        when(event) {
            is UserAuthEvents.EmailChanged -> {
                userAuthUIState.value = userAuthUIState.value.copy(
                    Email = event.Email
                )
                printState()
            }
            is UserAuthEvents.ContactChanged -> {
                userAuthUIState.value = userAuthUIState.value.copy(
                    Contact = event.Contact
                )
                printState()
            }
            is UserAuthEvents.RegisterButtonClicked -> {
                register()
            }
            is UserAuthEvents.LoginButtonClicked -> {
                login()
            }
        }
    }

    fun createUSerInFirebase(email: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d("AUTHCOMPLETE","Auth Completed.")
                Log.d("AUTHSUCCESS","isSuccesful = ${it.isSuccessful}")

            }
            .addOnFailureListener {
                Log.d("AUTHPENDING","Auth Pending.")
                Log.d("AUTHFAILED","Exception = ${it.message}")
                Log.d("AUTHFAILED","Exception = ${it.localizedMessage}")
            }
    }

    private fun register() {
        Log.d("REGISTER","Register Works")
        createUSerInFirebase(
            email = userAuthUIState.value.Email,
            password = userAuthUIState.value.Password
        )
    }
    private fun login() {
        Log.d("LOGIN","LOGIN Works")

    }
    private fun printState(){
        if (TAG != null) {
            Timber.tag(TAG).d("Inside_Print_State")
            Timber.tag(TAG).d(userAuthUIState.value.toString())
        }
    }
}