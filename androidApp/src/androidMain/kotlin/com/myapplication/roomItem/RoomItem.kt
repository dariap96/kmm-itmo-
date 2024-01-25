package com.myapplication.roomItem

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.myapplication.viewmodel.RoomItemViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoomItem(navController: NavHostController, roomId: Int) {
    val viewModel: RoomItemViewModel = koinViewModel()
    if (!viewModel.uiState.isDataFetched) {
        viewModel.getRoomItem(roomId)
        viewModel.uiState.isDataFetched = true
    }
    RoomItemScreen(
        navController = navController,
        uiState = viewModel.uiState,
        onManagerIdChange = viewModel::updateManagerId,
        onPriceChange = viewModel::updatePrice,
        onUpdate = viewModel::updateRoomItem,
        onEditButtonClick = viewModel::initEditMode
    )
}