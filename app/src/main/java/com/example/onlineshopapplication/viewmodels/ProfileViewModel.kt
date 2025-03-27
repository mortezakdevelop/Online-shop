package com.example.onlineshopapplication.viewmodels

import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapplication.data.models.profile.ProfileResponse
import com.example.onlineshopapplication.repository.home.ProfileRepository
import com.example.onlineshopapplication.utils.NetworkMessagesResponse
import com.example.onlineshopapplication.utils.NetworkRequestStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository):ViewModel() {

    private val _profileData = MutableLiveData<NetworkRequestStatus<ProfileResponse>>()
    val profileData:MutableLiveData<NetworkRequestStatus<ProfileResponse>> = _profileData

    init {
        viewModelScope.launch {
            delay(300)
            callProfileDetailApi()
        }
    }

    private fun callProfileDetailApi() = viewModelScope.launch {
        _profileData.value = NetworkRequestStatus.Loading()
        val response = profileRepository.getProfileDetail()
        _profileData.value = NetworkMessagesResponse(response).generateNetworkMessageResponse()
    }
}