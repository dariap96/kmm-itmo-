package com.myapplication.request

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.myapplication.viewmodel.RequestViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RequestList(navController: NavHostController) {
    val requestViewModel = koinViewModel<RequestViewModel>()
    val state by requestViewModel.items.collectAsState()

    RequestListScreen(uiState = state, navHostController = navController)

}