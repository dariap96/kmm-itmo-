package com.myapplication.auth

import com.myapplication.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(private val httpClient: HttpClient) {
    suspend fun whoAmI(): Flow<Resource<UserItem>> = flow {
        emit(Resource.loading())
        try {
            val data = httpClient.get(urlString = "/whoami").body<UserItem>()
            println("CURRENT USER: $data")
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }
}