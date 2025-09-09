package com.mosso.bimbo.login.presentantion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosso.bimbo.core.domain.UserPreferencesRepository
import com.mosso.bimbo.login.presentantion.state.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

    fun saveUserName(userName: String) = viewModelScope.launch {
        if (userName.isNotBlank()) {
            userPreferencesRepository.saveUserName(userName)
            _uiState.update { it.copy(null, true) }
        } else {
            _uiState.update { it.copy("Ingresa un nombre de usuario", false) }
        }
    }
}