package com.example.onlineshopapplication.utils

import com.example.onlineshopapplication.data.models.NetworkMessageResponseModel
import com.google.gson.Gson
import retrofit2.Response

open class NetworkMessagesResponse<T>(private val response:Response<T>) {
    open fun generateNetworkMessageResponse():NetworkRequestStatus<T>{
        return when{
            response.code() == 401 -> NetworkRequestStatus.Error("باید نبت نام کنید")
            response.code() == 422 -> {
                var errorMessage = ""
                if (response.errorBody()!=null){
                    val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), NetworkMessageResponseModel::class.java)
                    val message = errorResponse.message
                    println("Message: $message")

                    val errors = errorResponse.errors
                    errors?.forEach { (field,fieldError) ->
                        errorMessage = fieldError.joinToString()
                    }
                }
                NetworkRequestStatus.Error(errorMessage)
            }
            response.code() == 500 -> NetworkRequestStatus.Error("مجددا تلاش کنید")
            response.isSuccessful -> NetworkRequestStatus.Success(response.body()!!)
            else -> {NetworkRequestStatus.Error(response.message())}
        }
    }
}