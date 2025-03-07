package com.example.onlineshopapplication.ui.login

import academy.nouri.storeapp.data.models.login.BodyLogin
import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.databinding.FragmentLoginBinding
import com.example.onlineshopapplication.ui.MainActivity
import com.example.onlineshopapplication.ui.base.BaseFragment
import com.example.onlineshopapplication.utils.NetworkRequestStatus
import com.example.onlineshopapplication.utils.extensions.enableLoading
import com.example.onlineshopapplication.utils.extensions.hideKeyboard
import com.example.onlineshopapplication.utils.extensions.showSnackBar
import com.example.onlineshopapplication.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var mainActivity: MainActivity
    private lateinit var mobilePhone: String

    @Inject
    lateinit var bodyLogin: BodyLogin
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBottom.load(R.drawable.bg_circle)
            bodyLogin.hashCode = mainActivity.hashCode

            binding.btnInputWithMobilePhone.text = getString(R.string.putWithMobilePhone)
            btnInputWithMobilePhone.setOnClickListener {
                root.hideKeyboard()
                mobilePhone = edtMobilePhone.text.toString()
                if (mobilePhone.length == 11){
                    bodyLogin.login = mobilePhone
                    if (isNetworkAvailable) {
                        viewModel.callLoginApi(bodyLogin = bodyLogin)
                    }
                }
            }
        }
        loadLoginData()
        startRepeatingAnimation()
    }

    private fun startRepeatingAnimation() {
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                lifecycleScope.launch {
                    delay(2000)
                    binding.animationView.playAnimation()
                }
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }

        })
    }

    private fun loadLoginData() {
        binding.apply {
            viewModel.loginLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequestStatus.Loading -> {
                        btnInputWithMobilePhone.enableLoading(true)
                    }

                    is NetworkRequestStatus.Success -> {
                        btnInputWithMobilePhone.enableLoading(false)
                        val direction = LoginFragmentDirections.actionLoginFragmentToVerifyOtpFragment(mobilePhone)
                        findNavController().navigate(direction)
                    }

                    is NetworkRequestStatus.Error -> {
                        btnInputWithMobilePhone.enableLoading(false)
                        response.errorMessage?.let { root.showSnackBar(it, Snackbar.LENGTH_SHORT) }
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}