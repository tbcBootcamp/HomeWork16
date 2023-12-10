package com.example.hw16.ui.fragments.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw16.models.AuthRequestModel
import com.example.hw16.models.RegisterResponseModel
import com.example.hw16.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterViewState())
    val registerState: StateFlow<RegisterViewState> = _registerState

    fun register(email: String, password: String) {
        resetViewState()
        viewModelScope.launch {
            try {
                _registerState.value = _registerState.value.copy(loading = true)

                val response =
                    RetrofitClient.authService.register(AuthRequestModel(email, password))

                if (response.isSuccessful) {
                    _registerState.value = _registerState.value.copy(
                        loading = false,
                        registerResponse = response.body()!!
                    )
                } else {
                    _registerState.value = _registerState.value.copy(
                        error = response.errorBody()?.string() ?: "Unknown error",
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _registerState.value =
                    _registerState.value.copy(error = e.message ?: "Unknown error", loading = false)
            }
        }
    }

    private fun resetViewState() {
        _registerState.value = RegisterViewState()
    }

    data class RegisterViewState(
        val registerResponse: RegisterResponseModel? = null,
        val loading: Boolean = false,
        val error: String = ""
    )
}