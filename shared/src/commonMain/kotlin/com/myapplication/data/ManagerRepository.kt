package com.myapplication.data

import com.myapplication.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ManagerRepository(private val authorizedHttpClient: HttpClient) {
    suspend fun getManagers(directorId: Int): Flow<Resource<List<ManagerItem>>> = flow {
        emit(Resource.loading())
        try {
            val data =
                authorizedHttpClient.get(
//                    urlString = "/api/directorInfo/$directorId/managerInfos" todo think
                    urlString = "/api/managerInfo"
                )
                    .body<List<ManagerItem>>()
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }

    suspend fun getManagerInfoById(id: Int): Flow<Resource<ManagerItem>> = flow {
        emit(Resource.loading())
        try {
            val data = authorizedHttpClient.get(urlString = "/api/managerInfo/$id").body<ManagerItem>()
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }
}