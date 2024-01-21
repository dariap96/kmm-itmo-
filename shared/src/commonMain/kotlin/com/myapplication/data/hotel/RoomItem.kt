package com.myapplication.data.hotel

import kotlinx.serialization.Serializable

@Serializable
data class RoomItem(
    val id: Int,
    val number: Int,
    val capacity: Int,
    val floor: Int,
    val price: Double,
    val isVip: Boolean,
    val managerInfoId: Int,
    val hotelId: Int
)
