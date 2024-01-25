package com.myapplication.data.hotel

import com.myapplication.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RoomRepository(private val authorizedHttpClient: HttpClient) {
    suspend fun getRoomById(id: Int): Flow<Resource<RoomItem>> = flow {
        emit(Resource.loading())
        try {
            val data = authorizedHttpClient.get(urlString = "/api/rooms/$id").body<RoomItem>()
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }

    suspend fun updateRoom(id: Int, managerId: Int, price: Double): Flow<Resource<String>> = flow {
        emit(Resource.loading())
        try {
            val data = authorizedHttpClient.patch(urlString = "/api/rooms/$id") {
                contentType(ContentType.Application.Json)
                setBody(RoomUpdateRequest(managerId, price))
            }.body<String>()
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }

    suspend fun createRoom(number: Int, capacity: Int, floor: Int, price: Double, isVip: Boolean,
                            managerId: Int, hotelId: Int): Flow<Resource<String>> = flow {
        emit(Resource.loading())
        try {
            val data = authorizedHttpClient.post(urlString = "/api/rooms") {
                contentType(ContentType.Application.Json)
                setBody(CreateRoomRequest(number, capacity, floor, price, isVip, managerId, hotelId))
            }.body<String>()
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }
}