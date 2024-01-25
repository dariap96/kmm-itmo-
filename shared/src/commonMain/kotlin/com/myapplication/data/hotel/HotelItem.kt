package com.myapplication.data.hotel

import kotlinx.serialization.Serializable

@Serializable
data class HotelItem(
    val id: Int,
    val name: String,
    val rooms: List<RoomItem>,
    val stageCount: Int,
    val directorInfoId: Int,
    val rating: Double?,
)

@Serializable
data class HotelUpdateRequest(
    var name: String,
    var stageCount: Int
)