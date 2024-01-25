package com.myapplication.service

import com.myapplication.model.AppScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TopBarState(val isVisible: Boolean = false, val title: String = "Hotel")
class TopBarService {
    private val _state = MutableStateFlow(TopBarState())
    private val screenToTitleMap = mapOf<String, List<String>>(
        "Hotels" to listOf(
            AppScreen.RoomItem.name,
            AppScreen.CreateHotel.name,
            AppScreen.HotelItem.name,
            AppScreen.Hotels.name
        ), "Managers" to listOf(AppScreen.Managers.name)
    )
    val state = _state.asStateFlow()

    fun setVisibleState(visible: Boolean) {
        _state.update {
            it.copy(
                isVisible = visible
            )
        }
    }

    fun setTitle(title: String) {
        _state.update {
            it.copy(
                title = title
            )
        }
    }

    fun stateByScreen(screen: String) {
        if (screen !== AppScreen.Login.name) {
            this.setVisibleState(true)
        } else {
            this.setVisibleState(false)
        }
    }

    fun setTitleByScreen(screen: String) {
        screenToTitleMap.keys.forEach { key ->
            println("key $key $screen")
            if (screenToTitleMap[key]?.any { it == screen } == true) {
                println("setTitle")
                setTitle(key)
            }
        }
    }
}