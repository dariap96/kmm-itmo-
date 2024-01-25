package com.myapplication.viewmodel

import kotlinx.coroutines.MainScope

actual open class SharedViewModel {
    actual val sharedViewModelScope = MainScope()

    protected actual open fun onCleared() {}

    fun clear() {
        onCleared()
    }
}