package com.myapplication.model

data class Resource<out T>(val status: Status, val data: T?, val error: String?){

    companion object {
        //Handles success
        fun <T> success(data: T): Resource<T> = Resource(
            status = Status.SUCCESS, data = data, error = null)
        //Handles Loading
        fun <T> loading(data: T?=null): Resource<T> = Resource(
            status = Status.LOADING, data = data, error = null)
        //Handles Error
        fun <T> error(data: T?=null, error: String?=null): Resource<T> = Resource(
            status = Status.ERROR, data = data, error=error)
        fun<T> idle(data:T?) = Resource (status = Status.NONE, data = data, error = null)

    }
    enum class Status {
        NONE,
        SUCCESS,
        ERROR,
        LOADING
    }
}