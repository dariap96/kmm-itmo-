package com.myapplication.auth.signup

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUp(navController: NavHostController) {
    val viewModel: SignUpViewModel = koinViewModel()
    SignUpScreen(
        navController = navController,
        uiState = viewModel.uiState,
        onLoginChange = viewModel::updateLogin,
        onNameChange = viewModel::updateName,
        onSurnameChange = viewModel::updateSurname,
        onPasswordChange = viewModel::updatePassword
    )
}










