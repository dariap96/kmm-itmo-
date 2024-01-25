package com.myapplication.viewmodel

import com.myapplication.data.RequestItem
import com.myapplication.data.RequestRepository
import com.myapplication.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RequestUiData(
    val status: Resource.Status = Resource.Status.NONE,
    val data: List<RequestItem> = listOf(),
    val error: String? = null
)
class RequestViewModel(private val requestRepository: RequestRepository): SharedViewModel() {
    private val _items = MutableStateFlow(RequestUiData())

    val items = _items.asStateFlow()

    init {
        sharedViewModelScope.launch {
            requestRepository.getRequests().collect { result ->
                println("Hotels collect result")

                when(result.status) {
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