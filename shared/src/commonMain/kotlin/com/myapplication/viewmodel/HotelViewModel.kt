package com.myapplication.viewmodel

import com.myapplication.data.hotel.HotelItem
import com.myapplication.data.hotel.HotelRepository
import com.myapplication.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiData(
    val status: Resource.Status = Resource.Status.NONE, // todo make enum
    val data: List<HotelItem> = listOf(),
    val error: String? = null
)

class HotelViewModel(private val repository: HotelRepository): SharedViewModel() {
    private val _items = MutableStateFlow(UiData())

    val items = _items.asStateFlow()

    init {
        sharedViewModelScope.launch {
            repository.getHotels().collect { result ->
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