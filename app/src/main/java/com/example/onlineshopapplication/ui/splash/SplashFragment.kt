package com.example.onlineshopapplication.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.data.data_store.SessionManager
import com.example.onlineshopapplication.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.co.prnd.readmore.BuildConfig
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
        binding = FragmentSplashBinding.inflate(layoutInflater,container,false)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getVersionName()
    }

    private fun checkUserSession(){
        lifecycleScope.launch {
            val token = sessionManager.getToken.first()
            if (token == null){
                //login
            }else{
                //home
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getVersionName(){
        binding.versionTxt.text = "${getString(R.string.version)} : ${BuildConfig.BUILD_TYPE}"
    }
}