package com.example.onlineshopapplication.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class NetworkChecker @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest
) : ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableStateFlow(false)
    private var capabilities: NetworkCapabilities? = null

    fun checkNetwork(): MutableStateFlow<Boolean> {
        connectivityManager.registerNetworkCallback(networkRequest,this)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork
            if (activeNetwork == null){
                isNetworkAvailable.value = false
                return isNetworkAvailable
            }
            capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            if (capabilities == null){
                isNetworkAvailable.value = false
                return isNetworkAvailable
            }

            isNetworkAvailable.value = when {
                capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }else{
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    isNetworkAvailable.value = when(type){
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }
}