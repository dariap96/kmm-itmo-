package com.dipumba.ytsocialapp.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {

    init {
        fetchData()
    }


    fun fetchData(){

        viewModelScope.launch {
            delay(1000)
        }
    }

}

data class OnBoardingUiState(
    val shouldShowOnBoarding: Boolean = false,
    val isLoading: Boolean = false,
    val loadingErrorMessage: String? = null
)

data class HomePostsUiState(
    val isLoading: Boolean = false,
    val loadingErrorMessage: String? = null
)