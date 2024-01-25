package com.myapplication.viewmodel

import com.myapplication.data.hotel.RoomRepository
import com.myapplication.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateRoomViewModel(
    userService: UserService,
    private val repository: RoomRepository
): SharedViewModel() {
    private val _createRoomState = MutableStateFlow(CreateRoomState())
    val createRoomState = _createRoomState.asStateFlow()
    var currentUserId: Int? = null

    init {
        sharedViewModelScope.launch {
            currentUserId = userService.items.value.data?.id
        }
    }

    fun updateNumber(input: String) {
        _createRoomState.update {
            it.copy(
                number = input
            )
        }
    }

    fun updateCapacity(input: String) {
        _createRoomState.update {
            it.copy(
                capacity = input
            )
        }
    }

    fun updateFloor(input: String) {
        _createRoomState.update {
            it.copy(
                floor = input
            )
        }
    }

    fun updatePrice(input: String) {
        _createRoomState.update {
            it.copy(
                price = input
            )
        }
    }

    fun updateIsVip(input: String) {
        _createRoomState.update {
            it.copy(
                isVip = input
            )
        }
    }

    fun updateManagerId(input: String) {
        _createRoomState.update {
            it.copy(
                managerId = input
            )
        }
    }


    fun createRoom(hotelId: Int) {
        sharedViewModelScope.launch {
            repository.createRoom(
                createRoomState.value.number.toInt(),
                createRoomState.value.capacity.toInt(),
                createRoomState.value.floor.toInt(),
                createRoomState.value.price.toDouble(),
                createRoomState.value.isVip.toBoolean(),
                //createRoomState.value.managerId.toInt(),
                3,
                hotelId
            ).collect { result ->

                when (result.status) {
                    Resource.Status.LOADING -> {

                    }

                    Resource.Status.ERROR -> {

                    }

                    Resource.Status.SUCCESS -> {
                        _createRoomState.update {
                            it.copy(
                                roomCreated = true
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

data class CreateRoomState(
    var number: String = "",
    var capacity: String = "",
    var floor: String = "",
    var price: String = "",
    var isVip: String = "",
    var managerId: String = "",
    var hotelId: String = "",
    var roomCreated: Boolean = false
)
