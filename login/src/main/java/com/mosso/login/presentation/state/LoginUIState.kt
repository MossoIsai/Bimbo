package com.mosso.login.presentation.state

data class LoginUIState(
    val errorMessage: String? = null,
    val shouldNextScreen: Boolean = false,
)