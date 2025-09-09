package com.mosso.bimbo.login.presentantion.state

data class LoginUIState(
    val errorMessage: String? = null,
    val shouldNextScreen: Boolean = false,
)