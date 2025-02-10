package com.example.onlineshopapplication.data.models

import com.google.gson.annotations.SerializedName

data class NetworkMessageResponseModel(
    @SerializedName("errors")
    var errors: Map<String,List<String>>?,
    @SerializedName("message")
    var message: String?,
)
