package com.starorigins.crypstocks.ui.screens.userauth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import timber.log.Timber

class UserAuthViewModel: ViewModel() {
    private var userAuthUIState = mutableStateOf(UserAuthUIState())
    private val TAG = UserAuthViewModel::class.simpleName

    fun onEvent(event:UserAuthEvents) {
        when(event) {
            is UserAuthEvents.FullNameChanged -> {
                userAuthUIState.value = userAuthUIState.value.copy(
                    FullName = event.FullName
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
    private fun register() {

    }
    private fun login() {

    }
    private fun printState(){
        if (TAG != null) {
            Timber.tag(TAG).d("Inside_Print_State")
            Timber.tag(TAG).d(userAuthUIState.value.toString())
        }
    }
}