package com.example.onlineshopapplication.data.network

import academy.nouri.storeapp.data.models.login.BodyLogin
import academy.nouri.storeapp.data.models.login.ResponseLogin
import academy.nouri.storeapp.data.models.login.ResponseVerify
import com.example.onlineshopapplication.data.models.home.BannerResponse
import com.example.onlineshopapplication.data.models.home.DiscountResponse
import com.example.onlineshopapplication.data.models.profile.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {
    @POST("auth/login")
    suspend fun postLogin(@Body bodyLogin: BodyLogin): Response<ResponseLogin>

    @POST("auth/login/verify")
    suspend fun postVerify(@Body bodyVerify: BodyLogin): Response<ResponseVerify>

    @GET("auth/detail")
    suspend fun getProfileDetail(): Response<ProfileResponse>

    @GET("ad/swiper/{slug}")
    suspend fun getBannerList(@Path("slug") slug: String): Response<BannerResponse>

@GET("offers/discount/{slug}")
    suspend fun getDiscountList(@Path("slug") slug: String): Response<DiscountResponse>


}