package com.myapplication.di

import com.myapplication.auth.AuthRepository
import com.myapplication.auth.UserRepository
import com.myapplication.auth.login.LoginViewModel
import com.myapplication.data.ManagerRepository
import com.myapplication.data.RequestRepository
import com.myapplication.data.hotel.HotelRepository
import com.myapplication.data.hotel.RoomRepository
import com.myapplication.data.httpClient
import com.myapplication.service.TopBarService
import com.myapplication.viewmodel.CreateHotelViewModel
import com.myapplication.viewmodel.HotelItemViewModel
import com.myapplication.viewmodel.HotelViewModel
import com.myapplication.viewmodel.ManagerViewModel
import com.myapplication.viewmodel.SharedViewModel
import com.myapplication.viewmodel.CreateManagerViewModel
import com.myapplication.viewmodel.RequestViewModel
import com.myapplication.viewmodel.RoomItemViewModel
import com.myapplication.viewmodel.UserService
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
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
import org.koin.core.qualifier.named
import org.koin.dsl.module


expect inline fun <reified T : SharedViewModel> Module.sharedViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>

val bearerTokenStorage = mutableListOf<BearerTokens>()

val hotelModule = module {

    single(named("auth")) {
        httpClient {
            expectSuccess = true
            defaultRequest {
                url("http://10.0.2.2:8080")
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            bearerTokenStorage.last().accessToken,
                            bearerTokenStorage.last().refreshToken
                        )
                    }
                }
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

    single(named("noAuth")) {
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
        HotelRepository(get(named("auth")))
    }

    single {
        AuthRepository(get(named("auth")), get(named("noAuth")))
    }

    single {
        UserRepository(get(named("auth")))
    }

    single {
        TopBarService()
    }

    single {
        UserService(get())
    }

    single {
        ManagerRepository(get(named("auth")))
    }

    single {
        RoomRepository(get(named("auth")))
    }

    single {
        RequestRepository(get(named("auth")))
    }

    sharedViewModel {
        LoginViewModel(get(), get())
    }

    sharedViewModel {
        CreateManagerViewModel(get())
    }

    sharedViewModel {
        HotelViewModel(get())
    }

    sharedViewModel {
        HotelItemViewModel(get(), get())
    }

    sharedViewModel {
        CreateHotelViewModel(get(), get())
    }

    sharedViewModel {
        ManagerViewModel(get(), get())
    }

    sharedViewModel {
        RoomItemViewModel(get(), get(), get())
    }

    sharedViewModel {
        RequestViewModel(get())
    }
}