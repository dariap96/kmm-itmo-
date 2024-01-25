package com.myapplication.viewmodel

import com.myapplication.data.ManagerItem
import com.myapplication.data.ManagerRepository
import com.myapplication.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ManagersUiState(
    val status: Resource.Status = Resource.Status.NONE, // todo make enum
    val data: List<ManagerItem> = listOf(),
    val error: String? = null
)

class ManagerViewModel(
    private val repository: ManagerRepository,
    private val userService: UserService
) : SharedViewModel() {
    private val _items = MutableStateFlow(ManagersUiState())

    val items = _items.asStateFlow()

    init {
        val userId = requireNotNull(userService.items.value.data).id;

        sharedViewModelScope.launch {
            repository.getManagers(userId).collect { result ->
                println("Get Managers collect result")

                when (result.status) {
                    Resource.Status.LOADING -> {
                        _items.update {
                            it.copy(status = Resource.Status.LOADING)
                        }
                    }

                    Resource.Status.ERROR -> {
                        _items.update {
                            it.copy(status = Resource.Status.ERROR, error = it.error)
                        }
                    }

                    Resource.Status.SUCCESS -> {
                        _items.update {
                            it.copy(
                                status = Resource.Status.SUCCESS,
                                error = null,
                                data = result.data ?: listOf()
                            )
                        }
                    }

                    else -> {
                        _items.update { it }
                    }
                }

            }
        }
    }
}