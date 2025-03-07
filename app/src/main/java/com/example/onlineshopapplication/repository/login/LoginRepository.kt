package com.example.onlineshopapplication.repository.login

import academy.nouri.storeapp.data.models.login.BodyLogin
import com.example.onlineshopapplication.data.network.ApiServices
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiServices: ApiServices)  {
    suspend fun postLogin(body: BodyLogin) = apiServices.postLogin(body)
    suspend fun postVerify(body: BodyLogin) = apiServices.postVerify(body)
}