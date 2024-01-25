package com.myapplication.data

import com.myapplication.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RequestRepository(private val authorizedHttpClient: HttpClient) {
    suspend fun getRequests(): Flow<Resource<List<RequestItem>>> = flow {
        emit(Resource.loading())
        try {
            val data = authorizedHttpClient.get(urlString = "/api/requests").body<List<RequestItem>>()
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }
}