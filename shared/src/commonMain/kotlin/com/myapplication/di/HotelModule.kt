package com.myapplication.di

import com.myapplication.data.hotel.HotelRepository
import com.myapplication.data.httpClient
import com.myapplication.viewmodel.HotelViewModel
import com.myapplication.viewmodel.SharedViewModel
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module


expect inline fun <reified T : SharedViewModel> Module.sharedViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>

val hotelModule = module {
    single {
        httpClient {
            expectSuccess = true
            defaultRequest {
                url("http://10.0.2.2:8080")
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single {
        HotelRepository(get())
    }

    sharedViewModel {
        HotelViewModel(get())
    }
}