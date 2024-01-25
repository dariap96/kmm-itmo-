package com.myapplication.auth

import com.myapplication.di.bearerTokenStorage
import com.myapplication.model.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(private val authorizedHttpClient: HttpClient, private val unAuthorizedHttpClient: HttpClient) {

    suspend fun signUp(request: SignUpRequest, role: String): Flow<Resource<String>> = flow {
        emit(Resource.loading())
        try {
            val data = authorizedHttpClient.post(urlString = "/register/$role") {
                contentType(ContentType.Application.Json)
                setBody(request)
             }.body<String>()
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            println("${e.message} SIGNUP ERROR")
            emit(Resource.error(error = e.message))
        }
    }

    suspend fun login(request: LoginRequest): Flow<Resource<Token>> = flow {
        emit(Resource.loading())
        try {
            val data = unAuthorizedHttpClient.post(urlString = "/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
             }.body<Token>()
            bearerTokenStorage.add(BearerTokens(data.token, ""))
            emit(Resource.success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(error = e.message))
        }
    }
}