package com.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
class RequestItem(
    val id: Int,
    val fromClientId: Int,
    val hotelId: Int,
    val roomId: Int,
    val typeId: Int,
    val statusId: Int,
    val additionalInfo: String
)