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
    private val userRepository: UserRepository): SharedViewModel() {

    var uiState by mutableStateOf(HotelItemUiState())
        private set


    init {
        sharedViewModelScope.launch {
            //todo: pass hotel id
            //getHotelItem(id)
            var role = ""
            userRepository.whoAmI().collect { result ->
                role = result.data?.role!!.role
            }
            uiState = if (role === "DIRECTOR") {
                uiState.copy(isAbleToEdit = true)
            } else {
                uiState.copy(isAbleToEdit = false)
            }
        }
    }

    private suspend fun getHotelItem(id: Int) {
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
        uiState = uiState.copy(name = input, isUpdateButtonActive = true)
        uiState = uiState.copy(isUpdateButtonActive = true)
    }

    fun updateStageCount(input: String){
        uiState = uiState.copy(stages = input, isUpdateButtonActive = true)
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
                            isUpdateButtonActive = false,
                            isUpdating = false
                        )
                    }

                    Resource.Status.SUCCESS -> {
                        uiState = uiState.copy(
                            name = result.data!!.name,
                            stages = result.data.stageCount.toString(),
                            updateSucceed = true,
                            isUpdateButtonActive = false,
                            isUpdating = false
                        )
                    }

                    else -> {

                    }
                }

            }
        }
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
    var isAbleToEdit: Boolean = false,
    var currentHotelId: Int = 0
)