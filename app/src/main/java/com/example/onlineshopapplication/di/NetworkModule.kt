package com.example.onlineshopapplication.di

import com.example.onlineshopapplication.data.network.ApiServices
import com.example.onlineshopapplication.utils.BASE_URL
import com.example.onlineshopapplication.utils.CONNECTION_TIME_OUT
import com.example.onlineshopapplication.utils.NAMED_PING
import com.example.onlineshopapplication.utils.PING_INTERVAL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun profileBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun profileTimeOut() = CONNECTION_TIME_OUT

    @Provides
    @Singleton
    @Named(NAMED_PING)
    fun providePingTimeOut() = PING_INTERVAL

    @Provides
    @Singleton
    fun provideOkHttpClient(timeOut: Long, @Named(NAMED_PING) pingTimeOut: Long) =
        OkHttpClient.Builder()
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .pingInterval(pingTimeOut, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)
}