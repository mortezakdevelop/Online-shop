package com.example.onlineshopapplication.repository.home

import com.example.onlineshopapplication.data.network.ApiServices
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun getProfileDetail() = apiServices.getProfileDetail()
}