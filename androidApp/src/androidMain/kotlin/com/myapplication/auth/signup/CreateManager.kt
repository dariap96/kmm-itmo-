package com.myapplication.auth.signup

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.myapplication.viewmodel.CreateManagerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateManager(navController: NavHostController) {
    val viewModel: CreateManagerViewModel = koinViewModel()
    CreateManagerScreen(
        navController = navController,
        uiState = viewModel.uiState,
        onLoginChange = viewModel::updateLogin,
        onNameChange = viewModel::updateName,
        onSurnameChange = viewModel::updateSurname,
        onPasswordChange = viewModel::updatePassword,
        onSignUp = viewModel::signUp
    )
}










