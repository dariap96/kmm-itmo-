package com.myapplication.home

import androidx.compose.runtime.Composable
import com.dipumba.ytsocialapp.android.home.HomeScreen
import com.dipumba.ytsocialapp.android.home.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(
) {
    val viewModel: HomeScreenViewModel = koinViewModel()

    HomeScreen()
}