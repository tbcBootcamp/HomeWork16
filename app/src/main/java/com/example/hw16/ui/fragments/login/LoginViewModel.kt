package com.example.hw16.ui.fragments.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw16.models.AuthRequestModel
import com.example.hw16.models.LoginResponseModel
import com.example.hw16.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState: StateFlow<LoginViewState> = _loginState

    fun login(username: String, password: String) {
        resetViewState()
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)

                val response =
                    RetrofitClient.authService.login(AuthRequestModel(username, password))

                if (response.isSuccessful) {
                    _loginState.value = _loginState.value.copy(
                        loading = false,
                        loginResponse = response.body()!!
                    )
                } else {
                    _loginState.value = _loginState.value.copy(
                        error = response.errorBody()?.string() ?: "Unknown error",
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _loginState.value =
                    _loginState.value.copy(error = e.message ?: "Unknown error", loading = false)
            }
        }
    }

    private fun resetViewState() {
        _loginState.value = LoginViewState()
    }

    data class LoginViewState(
        val loginResponse: LoginResponseModel? = null,
        val loading: Boolean = false,
        val error: String = ""
    )
}



