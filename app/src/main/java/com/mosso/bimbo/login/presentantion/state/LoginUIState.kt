package com.mosso.bimbo.login.presentantion.state

sealed class LoginUIState {
    data class Error(val message: String) : LoginUIState()
    object OnNextScreen : LoginUIState()
    object Idle : LoginUIState()
}