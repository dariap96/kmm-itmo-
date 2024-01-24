package com.myapplication.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.myapplication.auth.AuthRepository
import com.myapplication.auth.LoginRequest
import com.myapplication.model.Resource
import com.myapplication.viewmodel.SharedViewModel
import kotlinx.coroutines.launch


class LoginViewModel(
    private val authRepository: AuthRepository
): SharedViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    //todo: clean uiState.autherrormessage for launcheffect to wotk correctly

    fun signIn(){
        sharedViewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)

            authRepository.login(LoginRequest(uiState.login, uiState.password)).collect { result ->

                when(result.status) {
                    Resource.Status.LOADING -> {
                    }

                    Resource.Status.ERROR -> {
                        uiState = uiState.copy(
                            isAuthenticating =  false,
                            authErrorMessage = "wrong login or password"
                        )
                    }

                    Resource.Status.SUCCESS -> {
                        uiState = uiState.copy(
                        isAuthenticating =  false,
                        authenticationSucceed = true
                        )
                        println("user logged in")
                    }

                    else -> {
                       println("internal error")
                    }
                }

            }
        }
    }

    fun updateLogin(input: String){
        uiState = uiState.copy(login = input)
    }

    fun updatePassword(input: String){
        uiState = uiState.copy(password = input)
    }
}

data class LoginUiState(
    var login: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = "",
    var authenticationSucceed: Boolean = false
)













