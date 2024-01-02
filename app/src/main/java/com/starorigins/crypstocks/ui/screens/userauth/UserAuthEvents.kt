package com.starorigins.crypstocks.ui.screens.userauth

sealed class UserAuthEvents {
    data class EmailChanged(var Email:String) : UserAuthEvents()
    data class ContactChanged(var Contact:String) : UserAuthEvents()
    data class UserNameChanged(var Username:String) : UserAuthEvents()
    data class PasswordChanged(var Password:String) : UserAuthEvents()

    object RegisterButtonClicked: UserAuthEvents()
    object LoginButtonClicked: UserAuthEvents()
}