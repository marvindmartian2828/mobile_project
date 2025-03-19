package com.example.projdraft_autovitals.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// UI state data class to manage input fields and login state
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class LoginViewModel : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    private val validUsername = "test"
    private val validPassword = "123"

    fun updateUsername(username: String) {
        uiState = uiState.copy(username = username)
    }

    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            delay(1000) // Simulate API delay

            if (uiState.username == validUsername && uiState.password == validPassword) {
                uiState = uiState.copy(isLoading = false)
                onSuccess()
            } else {
                uiState = uiState.copy(isLoading = false, errorMessage = "Invalid username or password.")
            }
        }
    }
}
