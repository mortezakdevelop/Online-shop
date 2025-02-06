package com.example.onlineshopapplication.ui.splash

import android.annotation.SuppressLint
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.data.data_store.SessionManager
import com.example.onlineshopapplication.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

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
            Handler(Looper.getMainLooper()).postDelayed({
                if (token == null) {
                    //login
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                } else {
                    //home
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            },3000)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getVersionName() {
/*
        binding.versionTxt.text = "${getString(R.string.version)} : ${getString(R.string.versionName)}"
*/
    }
}