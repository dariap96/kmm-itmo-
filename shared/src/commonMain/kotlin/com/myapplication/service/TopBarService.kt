package com.myapplication.service

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.myapplication.model.AppScreen

data class TopBarState(val isVisible: Boolean = false)
class TopBarService {
    var state by mutableStateOf(TopBarState())

    fun setVisibleState(visible: Boolean) {
        state = state.copy(isVisible = visible)
    }

    fun stateByScreen(screen: String) {
        if (screen !== AppScreen.Login.name) {
            this.setVisibleState(true)
        } else {
            this.setVisibleState(false)
        }
    }
}