package com.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.myapplication.auth.UserRepository
import com.myapplication.data.hotel.HotelRepository
import com.myapplication.data.hotel.RoomItem
import com.myapplication.model.Resource
import kotlinx.coroutines.launch

class HotelItemViewModel(
    private val repository: HotelRepository,
    private val userRepository: UserViewModel): SharedViewModel() {

    var uiState by mutableStateOf(HotelItemUiState())
        private set


    init {
        sharedViewModelScope.launch {
            var role = userRepository.items.value.data?.role?.role
            uiState = if (role === "DIRECTOR") {
                uiState.copy(isAbleToEdit = true)
            } else {
                uiState.copy(isAbleToEdit = false)
            }
        }
    }

    fun getHotelItem(id: Int) = sharedViewModelScope.launch {
        repository.getHotelById(id).collect { result ->
            println("collect hotelItem result")

            when(result.status) {
                Resource.Status.LOADING -> {
                    uiState = uiState.copy(
                        isUpdating = true
                    )
                }

                Resource.Status.ERROR -> {
                    uiState = uiState.copy(
                        isUpdating = false
                    )
                }

                Resource.Status.SUCCESS -> {
                    uiState = uiState.copy(
                        name = result.data!!.name,
                        stages = result.data.stageCount.toString(),
                        rooms = result.data.rooms,
                        directorInfoId = result.data.directorInfoId,
                        rating = result.data.rating,
                        isUpdating = false,
                        currentHotelId = id
                    )
                }

                else -> {
                }
            }
        }
    }

    fun updateName(input: String){
        uiState = uiState.copy(name = input)
    }

    fun updateStageCount(input: String){
        uiState = uiState.copy(stages = input)
    }

    fun updateHotelItem() {
        sharedViewModelScope.launch {
            repository.updateHotel(uiState.currentHotelId).collect { result ->

                println("updating hotelItem data ")

                when(result.status) {

                    Resource.Status.LOADING -> {
                        uiState = uiState.copy(isUpdating = true)
                    }

                    Resource.Status.ERROR -> {
                        uiState = uiState.copy(
                            name = result.data!!.name,
                            stages = result.data.stageCount.toString(),
                            updateSucceed = false,
                            isUpdating = false
                        )
                    }

                    Resource.Status.SUCCESS -> {
                        uiState = uiState.copy(
                            name = result.data!!.name,
                            stages = result.data.stageCount.toString(),
                            updateSucceed = true,
                            isUpdateButtonActive = false,
                            isUpdating = false,
                            isEditMode = false
                        )
                    }

                    else -> {

                    }
                }

            }
        }
    }

    fun initEditMode() {
        uiState = uiState.copy(isEditMode = true, isUpdateButtonActive = true)
    }
}

data class HotelItemUiState(
    var name: String = "",
    var stages: String = "",
    var rooms: List<RoomItem> = listOf(),
    var directorInfoId: Int = 0,
    var rating: Double? = 0.0,
    var isUpdating: Boolean = false,
    var updateErrorMessage: String? = "",
    var updateSucceed: Boolean = false,
    var isUpdateButtonActive: Boolean = false,
    var isAbleToEdit: Boolean = true,
    var currentHotelId: Int = 0,
    var isEditMode: Boolean = true
)