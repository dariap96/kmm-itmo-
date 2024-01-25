package com.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.myapplication.data.ManagerItem
import com.myapplication.data.ManagerRepository
import com.myapplication.data.hotel.RoomRepository
import com.myapplication.model.Resource
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomItemViewModel(
    private val repository: RoomRepository,
    private val userService: UserService,
    private val managerRepository: ManagerRepository): SharedViewModel() {

    var uiState by mutableStateOf(RoomItemUiState())
        private set


    init {
        sharedViewModelScope.launch {
            val role = userService.items.value.data?.role?.role
            val userId = userService.items.value.data!!.id

            uiState = if (role == "DIRECTOR") {
                uiState.copy(isAbleToEdit = true)
            } else {
                uiState.copy(isAbleToEdit = false)
            }

            managerRepository.getManagers(userId).collect { result ->
                println("Get Managers collect result in roomItem viewModel")

                when (result.status) {
                    Resource.Status.LOADING -> {
                    }

                    Resource.Status.ERROR -> {

                    }

                    Resource.Status.SUCCESS -> {
                        uiState = uiState.copy(
                            managerInfoList = result.data!!
                        )
                    }

                    else -> {
                    }
                }

            }
        }
    }

    fun getRoomItem(id: Int) = sharedViewModelScope.launch {
        repository.getRoomById(id).collect { result ->
            println("collect roomItem result, $result")

            when(result.status) {
                Resource.Status.LOADING -> {
                }

                Resource.Status.ERROR -> {
                    uiState = uiState.copy(
                        isUpdating = false
                    )
                }

                Resource.Status.SUCCESS -> {
                    uiState = uiState.copy(
                        number = result.data!!.number,
                        capacity = result.data.capacity,
                        floor = result.data.floor,
                        isVip = result.data.isVip,
                        price = result.data.price,
                        managerInfoId = result.data.managerInfoId,
                        currentRoomId = result.data.id,
                        isUpdating = false
                    )
                    managerRepository.getManagerInfoById(result.data.managerInfoId).collect { result1 ->
                        when (result1.status) {
                            Resource.Status.ERROR -> {

                            }

                            Resource.Status.SUCCESS -> {
                                uiState = uiState.copy(
                                    managerLogin  =result1.data!!.manager.login,
                                    managerName = result1.data!!.manager.name,
                                    managerSurname = result1.data.manager.surname,
                                    managerId = result1.data.id
                                )
                            }

                            else -> {}
                        }
                    }
                }

                else -> {
                }
            }
        }
    }

    fun updateManager(managerInfo: ManagerItem) {
        uiState = uiState.copy(
            managerInfoId = managerInfo.id,
            managerName = managerInfo.manager.name,
            managerSurname = managerInfo.manager.surname,
            managerLogin = managerInfo.manager.login,
            managerId = managerInfo.manager.id
            )
    }

    fun updatePrice(input: String){
        uiState = uiState.copy(price = input.toDouble())
    }

    fun updateRoomItem() {
        sharedViewModelScope.launch {
            repository.updateRoom(uiState.currentRoomId, uiState.managerId,
                uiState.price).collect { result ->

                println("updating roomItem data ")
                when(result.status) {

                    Resource.Status.LOADING -> {
                        uiState = uiState.copy(isUpdating = true)
                    }

                    Resource.Status.ERROR -> {
                        uiState = uiState.copy(
                            updateSucceed = false,
                            isUpdating = false
                        )
                    }

                    Resource.Status.SUCCESS -> {
                        uiState = uiState.copy(
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

data class RoomItemUiState(
    var number: Int = 0,
    var capacity: Int = 0,
    var floor: Int = 0,
    var managerInfoId: Int = 0,
    var isVip: Boolean = false,
    var price: Double = 0.0,
    var isUpdating: Boolean = false,
    var updateErrorMessage: String? = "",
    var updateSucceed: Boolean = false,
    var isUpdateButtonActive: Boolean = false,
    var isAbleToEdit: Boolean = false,
    var currentRoomId: Int = 0,
    var isEditMode: Boolean = false,
    var isDataFetched: Boolean = false,
    var managerName: String? = "",
    var managerSurname: String? = "",
    var managerId: Int = 0,
    var managerInfoList: List<ManagerItem> = listOf(),
    var managerLogin: String = ""
)