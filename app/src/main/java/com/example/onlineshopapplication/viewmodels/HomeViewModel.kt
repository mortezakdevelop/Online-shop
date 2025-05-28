package com.example.onlineshopapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapplication.data.models.home.BannerResponse
import com.example.onlineshopapplication.data.models.home.DiscountResponse
import com.example.onlineshopapplication.repository.home.HomeRepository
import com.example.onlineshopapplication.utils.ELECTRONIC_DEVICES
import com.example.onlineshopapplication.utils.GENERAL
import com.example.onlineshopapplication.utils.NetworkMessagesResponse
import com.example.onlineshopapplication.utils.NetworkRequestStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _bannerList = MutableLiveData<NetworkRequestStatus<BannerResponse>>()
    val bannerList: MutableLiveData<NetworkRequestStatus<BannerResponse>> = _bannerList

    private val _discountList = MutableLiveData<NetworkRequestStatus<DiscountResponse>>()
    val discountList: MutableLiveData<NetworkRequestStatus<DiscountResponse>> = _discountList


    init {
        viewModelScope.launch {
            delay(300)
            getBannerList()
            getDiscountList()
        }
    }

    private fun getBannerList() = viewModelScope.launch {
        _bannerList.value = NetworkRequestStatus.Loading()
        val response = homeRepository.getBannersList(GENERAL)
        _bannerList.value = NetworkMessagesResponse(response).generateNetworkMessageResponse()
    }

    private fun getDiscountList() = viewModelScope.launch {
        _discountList.value = NetworkRequestStatus.Loading()
        val response = homeRepository.getDiscountList(ELECTRONIC_DEVICES)
        _discountList.value = NetworkMessagesResponse(response).generateNetworkMessageResponse()
    }
}