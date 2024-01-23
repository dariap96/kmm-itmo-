package com.myapplication.auth.login

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun Login(navController: NavHostController) {
    val viewModel: LoginViewModel = koinViewModel()
    LoginScreen(
        navController = navController,
        uiState = viewModel.uiState,
        onLoginChange = viewModel::updateLogin,
        onPasswordChange = viewModel::updatePassword,
        onSignIn = viewModel::signIn
    )
}