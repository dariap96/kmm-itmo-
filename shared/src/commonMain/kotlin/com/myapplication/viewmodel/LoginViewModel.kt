package com.myapplication.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.myapplication.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
//    private val signInUseCase: SignInUseCase,
//    private val dataStore: DataStore<UserSettings>
): SharedViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun signIn(){
        sharedViewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)

//            val authResultData = signInUseCase(uiState.email, uiState.password)
//
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

    fun updatePassword(input: String){
        uiState = uiState.copy(password = input)
    }
}

data class LoginUiState(
    var login: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceed: Boolean = false
)













