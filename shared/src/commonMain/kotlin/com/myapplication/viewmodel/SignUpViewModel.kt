package com.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.myapplication.auth.AuthRepository
import com.myapplication.auth.SignUpRequest
import com.myapplication.model.Resource
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository
): SharedViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    //todo: clean uiState.autherrormessage for launcheffect to wotk correctly

    fun signUp(){
        sharedViewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)

            authRepository.signUp(SignUpRequest(uiState.name,
                uiState.surname,
                uiState.login,
                uiState.password), uiState.role).collect { result ->

                when(result.status) {
                    Resource.Status.LOADING -> {
                    }

                    Resource.Status.ERROR -> {
                        uiState = uiState.copy(
                            isAuthenticating =  false,
                            authErrorMessage = "such login already exists"
                        )
                    }
                    //todo: handle error when one or more fields aren't filled

                    Resource.Status.SUCCESS -> {
                        uiState = uiState.copy(
                            isAuthenticating =  false,
                            authenticationSucceed = false
                        )
                        println("user signed up")
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

    fun updateName(input: String){
        uiState = uiState.copy(name = input)
    }

    fun updateSurname(input: String){
        uiState = uiState.copy(surname = input)
    }

    fun updatePassword(input: String){
        uiState = uiState.copy(password = input)
    }
}

data class SignUpUiState(
    var login: String = "",
    var name: String = "",
    var surname: String = "",
    var password: String = "",
    var role: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = "",
    var authenticationSucceed: Boolean = false
)












