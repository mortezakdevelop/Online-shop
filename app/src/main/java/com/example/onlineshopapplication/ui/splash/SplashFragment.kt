package com.example.onlineshopapplication.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kr.co.prnd.readmore.BuildConfig

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

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

    }

    @SuppressLint("SetTextI18n")
    private fun getVersionName(){
        binding.versionTxt.text = "${getString(R.string.version)} : ${BuildConfig.BUILD_TYPE}"
    }
}