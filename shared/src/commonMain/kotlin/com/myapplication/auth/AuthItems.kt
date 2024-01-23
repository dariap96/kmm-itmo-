package com.myapplication.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    var name: String,
    var surname: String,
    var login: String,
    var password: String
)

@Serializable
data class LoginRequest(
    val login: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String
)

@Serializable
data class UserItem(
    val id: Int,
    val name: String,
    val surname: String,
    val bDay: String?,
    val login: String,
    val password: String,
    val email: String?,
    val role: RoleItem
)

@Serializable
data class RoleItem (
    val id: Int,
    val role: String
)

@Serializable
data class Token(
    val token: String
)