package com.myapplication.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.myapplication.viewmodel.ManagerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ManagerList(navController: NavHostController) {
    val managerViewModel = koinViewModel<ManagerViewModel>()
    val state by managerViewModel.items.collectAsState()

    ManagerListScreen(uiState = state, navHostController = navController)

}