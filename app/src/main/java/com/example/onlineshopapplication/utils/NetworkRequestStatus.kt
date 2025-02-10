package com.example.onlineshopapplication.utils

sealed class NetworkRequestStatus<T>(
    val data:T? = null,
    val errorMessage:String?=null
) {

    class Loading<T> : NetworkRequestStatus<T>()
    class Success<T>(data:T) : NetworkRequestStatus<T>(data = data)
    class Error<T>(message:String): NetworkRequestStatus<T>(errorMessage = message)
}