package com.example.onlineshopapplication.di

import com.example.onlineshopapplication.BuildConfig
import com.example.onlineshopapplication.data.data_store.SessionManager
import com.example.onlineshopapplication.data.network.ApiServices
import com.example.onlineshopapplication.utils.ACCEPT
import com.example.onlineshopapplication.utils.APPLICATION_JSON
import com.example.onlineshopapplication.utils.AUTHORIZATION
import com.example.onlineshopapplication.utils.BASE_URL
import com.example.onlineshopapplication.utils.BEARER
import com.example.onlineshopapplication.utils.CONNECTION_TIME_OUT
import com.example.onlineshopapplication.utils.NAMED_PING
import com.example.onlineshopapplication.utils.PING_INTERVAL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        timeOut: Long,
        @Named(NAMED_PING) pingTimeOut: Long,
        interceptor: HttpLoggingInterceptor,
        sessionManager: SessionManager
    ) =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = runBlocking {
                    sessionManager.getToken.first().toString()
                }
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader(AUTHORIZATION, "$BEARER $token")
                    it.addHeader(ACCEPT, APPLICATION_JSON)
                }.build())
            }.also { client ->
                client.addInterceptor(interceptor)
            }
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