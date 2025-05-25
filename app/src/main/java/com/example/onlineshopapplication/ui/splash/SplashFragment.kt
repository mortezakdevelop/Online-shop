package com.example.onlineshopapplication.ui.splash

import android.annotation.SuppressLint
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.onlineshopapplication.BuildConfig
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.data.data_store.SessionManager
import com.example.onlineshopapplication.databinding.FragmentSplashBinding
import com.example.onlineshopapplication.ui.MainActivity
import com.example.onlineshopapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var sessionManager: SessionManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getVersionName()
        checkUserSession()
    }

    private fun checkUserSession() {
        lifecycleScope.launch {
            val token = sessionManager.getToken.first()
            Log.d("splash", "checkUserSession: $token")
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().popBackStack(R.id.splashFragment, true)
                if (isNetworkAvailable){
                    if (token == null) {
                        //login
                        findNavController().navigate(R.id.actionSplashToLogin)
                    } else {
                        //home
                        findNavController().navigate(R.id.actionSplashToMain)
                    }
                }
            },3000)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getVersionName() {
        binding.versionTxt.text = BuildConfig.VERSION_NAME
    }
}