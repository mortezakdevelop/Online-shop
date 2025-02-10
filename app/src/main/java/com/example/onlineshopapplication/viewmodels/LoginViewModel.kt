package com.example.onlineshopapplication.viewmodels

import academy.nouri.storeapp.data.models.login.BodyLogin
import academy.nouri.storeapp.data.models.login.ResponseLogin
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapplication.repository.login.LoginRepository
import com.example.onlineshopapplication.utils.NetworkMessagesResponse
import com.example.onlineshopapplication.utils.NetworkRequestStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    private val _loginLiveData = MutableLiveData<NetworkRequestStatus<ResponseLogin>>()
    val loginLiveData:LiveData<NetworkRequestStatus<ResponseLogin>> = _loginLiveData

    fun callLoginApi(bodyLogin: BodyLogin) = viewModelScope.launch {
        _loginLiveData.value = NetworkRequestStatus.Loading()
        val response = loginRepository.postLogin(bodyLogin)
        _loginLiveData.value = NetworkMessagesResponse(response).generateNetworkMessageResponse()
    }
}