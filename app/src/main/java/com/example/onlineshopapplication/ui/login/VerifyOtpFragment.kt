package com.example.onlineshopapplication.ui.login

import academy.nouri.storeapp.data.models.login.BodyLogin
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.NetworkRequest
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.data.data_store.SessionManager
import com.example.onlineshopapplication.databinding.FragmentVerifyOtpBinding
import com.example.onlineshopapplication.ui.base.BaseFragment
import com.example.onlineshopapplication.utils.NetworkRequestStatus
import com.example.onlineshopapplication.utils.extensions.enableLoading
import com.example.onlineshopapplication.utils.extensions.hideKeyboard
import com.example.onlineshopapplication.utils.extensions.showSnackBar
import com.example.onlineshopapplication.utils.isCalledVerify
import com.example.onlineshopapplication.viewmodels.LoginViewModel
import com.goodiebag.pinview.Pinview
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class VerifyOtpFragment : BaseFragment() {
    private var _binding: FragmentVerifyOtpBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var body: BodyLogin

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel by viewModels<LoginViewModel>()
    private val args by navArgs<VerifyOtpFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVerifyOtpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCalledVerify = false
        args.let {
            body.login = it.phone
        }
        binding.apply {
            bottomImg.load(R.drawable.bg_circle)
            pinView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            pinView.setPinViewEventListener(object : Pinview.PinViewEventListener {
                override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
                    body.code = pinview?.value?.toInt()
                    if (isNetworkAvailable)
                        viewModel.callVerifyApi(body)
                }
            })
            sendAgainBtn.setOnClickListener {
                if (isNetworkAvailable)
                    viewModel.callLoginApi(body)
                handleTimer().cancel()
            }
        }
        lifecycleScope.launch {
            delay(500)
            handleTimer().start()
        }
        loadLoginData()
        loadVerifyData()
    }

    private fun handleTimer(): CountDownTimer {
        binding.apply {
            return object : CountDownTimer(60_000, 1_000) {
                @SuppressLint("SetTextI18n")
                override fun onTick(millis: Long) {
                    sendAgainBtn.isVisible = false
                    timerTxt.isVisible = true
                    timerProgress.isVisible = true
                    timerTxt.text = "${millis / 1000} ${getString(R.string.second)}"
                    timerProgress.setProgressCompat((millis / 1000).toInt(), true)
                    if (millis < 1000)
                        timerProgress.progress = 0
                }

                override fun onFinish() {
                    sendAgainBtn.isVisible = true
                    timerTxt.isVisible = false
                    timerProgress.visibility = View.GONE
                    timerProgress.progress = 0
                }
            }
        }
    }

    private fun loadLoginData() {
        binding.apply {
            viewModel.loginLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequestStatus.Loading -> {
                        sendAgainBtn.enableLoading(true)
                    }

                    is NetworkRequestStatus.Success -> {
                        sendAgainBtn.enableLoading(false)
                        response.data?.let {
                            handleTimer().start()
                        }
                    }

                    is NetworkRequestStatus.Error -> {
                        sendAgainBtn.enableLoading(false)
                        root.showSnackBar(response.errorMessage.toString(),0)
                    }
                }
            }
        }
    }

    private fun loadVerifyData() {
        binding.apply {
            viewModel.verifyLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequestStatus.Loading -> {
                        timerLay.alpha = 0.3f
                    }

                    is NetworkRequestStatus.Success -> {
                        timerLay.alpha = 1.0f
                        response.data?.let { data ->
                            lifecycleScope.launch {
                                sessionManager.saveToken(data.accessToken.toString())
                            }
                            root.hideKeyboard()
                            findNavController().popBackStack(R.id.verifyOtpFragment, true)
                            findNavController().popBackStack(R.id.loginFragment, true)
                            //Home
                            findNavController().navigate(R.id.actionSplashToMain)
                        }
                    }

                    is NetworkRequestStatus.Error -> {
                        timerLay.alpha = 1.0f
                        root.showSnackBar(response.errorMessage.toString(),0)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        handleTimer().cancel()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}