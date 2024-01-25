package com.myapplication.viewmodel

import com.myapplication.data.hotel.HotelRepository
import com.myapplication.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateHotelViewModel(
    userService: UserService,
    private val repository: HotelRepository): SharedViewModel() {
    private val _createHotelState = MutableStateFlow(CreateHotelState())
    val createHotelState = _createHotelState.asStateFlow()
    var currentUserId: Int? = null

    init {
        sharedViewModelScope.launch {
            currentUserId = userService.items.value.data?.id
        }
    }

    fun addDirectorId(id: Int) {
        _createHotelState.update {
            it.copy(
                directorId = id
            )
        }
    }

    fun changeName(name: String) {
        _createHotelState.update {
            it.copy(
                name = name
            )
        }
    }

    fun changeStageCount(stageCount: String) {
        _createHotelState.update {
            it.copy(
                stageCount = stageCount
            )
        }
    }

    fun createHotel() {
        sharedViewModelScope.launch {
            repository.createHotel(
                currentUserId!!, createHotelState.value.name,
                createHotelState.value.stageCount.toInt()
            ).collect { result ->

                when (result.status) {
                    Resource.Status.LOADING -> {

                    }

                    Resource.Status.ERROR -> {

                    }

                    Resource.Status.SUCCESS -> {
                        _createHotelState.update {
                            it.copy(
                                hotelCreated = true
                            )
                        }
                    }

                    else -> {

                    }
                }

            }
        }
    }
}

data class CreateHotelState(
    var name: String = "",
    var stageCount: String = "",
    var directorId: Int? = null,
    var hotelCreated: Boolean = false
)
