package com.myapplication.viewmodel

import com.myapplication.auth.UserItem
import com.myapplication.auth.UserRepository
import com.myapplication.model.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserData(
    val status: Resource.Status = Resource.Status.NONE,
    val data: UserItem? = null,
    val error: String? = null
)

class UserService(private val repository: UserRepository) {
    private val _items = MutableStateFlow(UserData())
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val items = _items.asStateFlow()

    fun getWhoAmI() = coroutineScope.launch {
        repository.whoAmI().collect { result ->
            println("get current user data $result")

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
                            data = result.data
                        )
                    }
                    val user = result.data
                }

                else -> {
                    _items.update { it }
                }
            }

        }
    }
}