package com.myapplication.data.hotel

import com.myapplication.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HotelRepository(private val authorizedHttpClient: HttpClient) {
    suspend fun getHotels(): Flow<Resource<List<HotelItem>>> = flow {
        emit(Resource.loading())
        try {
            val data = authorizedHttpClient.get(urlString = "/api/hotels").body<List<HotelItem>>()
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }
}