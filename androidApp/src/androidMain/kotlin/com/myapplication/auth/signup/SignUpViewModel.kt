package com.myapplication.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignUpViewModel(
//    private val signUpUseCase: SignUpUseCase,
//    private val dataStore: DataStore<UserSettings>
): ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    fun signUp(){
        viewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)

         //   val authResultData = signUpUseCase(uiState.email, uiState.username, uiState.password)

//            uiState = when(authResultData){
//                is Result.Error -> {
//                    uiState.copy(
//                        isAuthenticating = false,
//                        authErrorMessage = authResultData.message
//                    )
//                }
//                is Result.Success -> {
//                    dataStore.updateData {
//                        authResultData.data!!.toUserSettings()
//                    }
//                    uiState.copy(
//                        isAuthenticating = false,
//                        authenticationSucceed = true
//                    )
//                }
//            }
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
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceed: Boolean = false
)












