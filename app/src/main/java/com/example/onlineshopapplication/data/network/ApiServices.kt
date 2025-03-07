package com.example.onlineshopapplication.data.network

import academy.nouri.storeapp.data.models.login.BodyLogin
import academy.nouri.storeapp.data.models.login.ResponseLogin
import academy.nouri.storeapp.data.models.login.ResponseVerify
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("auth/login")
    suspend fun postLogin(@Body bodyLogin:BodyLogin):Response<ResponseLogin>

    @POST("auth/login/verify")
    suspend fun postVerify(@Body bodyVerify:BodyLogin):Response<ResponseVerify>
}