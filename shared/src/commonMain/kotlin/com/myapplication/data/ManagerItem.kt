package com.myapplication.data

import com.myapplication.auth.UserItem
import com.myapplication.data.hotel.RoomItem
import kotlinx.serialization.Serializable

@Serializable
data class ManagerItem (
    val id: Int,
    val manager: UserItem,
    val rooms: List<RoomItem>
)