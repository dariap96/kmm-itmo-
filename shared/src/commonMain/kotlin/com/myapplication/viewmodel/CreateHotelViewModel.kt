package com.myapplication.viewmodel

import com.myapplication.data.hotel.HotelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateHotelViewModel(hotelRepository: HotelRepository): SharedViewModel() {
    private val _createHotelState = MutableStateFlow(CreateHotelState())
    val createHotelState = _createHotelState.asStateFlow()

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

    fun changeStageCount(stageCount: Int) {
        _createHotelState.update {
            it.copy(
                stageCount = stageCount
            )
        }
    }

    fun save() {
        println(createHotelState.value)
    }
}

data class CreateHotelState(
    var name: String = "",
    var stageCount: Int = 0,
    var directorId: Int? = null,
)
