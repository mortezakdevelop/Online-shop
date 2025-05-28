package com.example.onlineshopapplication.repository.home

import com.example.onlineshopapplication.data.network.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun getBannersList(slug:String) = apiServices.getBannerList(slug)
    suspend fun getDiscountList(slug:String) = apiServices.getDiscountList(slug)

}