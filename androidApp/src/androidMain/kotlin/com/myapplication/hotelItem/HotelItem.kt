package com.myapplication.hotelItem

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.myapplication.viewmodel.HotelItemViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HotelItem(navController: NavHostController, hotelId: Int) {
    val viewModel: HotelItemViewModel = koinViewModel()
    viewModel.getHotelItem(hotelId)
    HotelItemScreen(
        navController = navController,
        uiState = viewModel.uiState,
        onNameChange = viewModel::updateName,
        onStageCountChange = viewModel::updateStageCount,
        onUpdate = viewModel::updateHotelItem,
        onEditButtonClick = viewModel::initEditMode
    )
}