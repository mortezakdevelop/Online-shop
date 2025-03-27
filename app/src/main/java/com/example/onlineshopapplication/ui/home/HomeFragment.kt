package com.example.onlineshopapplication.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.request.CachePolicy
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.databinding.FragmentHomeBinding
import com.example.onlineshopapplication.databinding.FragmentVerifyOtpBinding
import com.example.onlineshopapplication.ui.login.LoginFragmentDirections
import com.example.onlineshopapplication.utils.NetworkRequestStatus
import com.example.onlineshopapplication.utils.extensions.enableLoading
import com.example.onlineshopapplication.utils.extensions.loadImage
import com.example.onlineshopapplication.utils.extensions.showSnackBar
import com.example.onlineshopapplication.utils.isCalledVerify
import com.example.onlineshopapplication.viewmodels.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel by activityViewModels<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        loadProfileDetailData()
        return binding.root
    }

    private fun loadProfileDetailData() {
        binding.apply {
            profileViewModel.profileData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequestStatus.Loading -> {
                        avatarLoading.isVisible = true
                    }

                    is NetworkRequestStatus.Success -> {
                        avatarLoading.isVisible = false
                        response.data.let { profileResponse ->
                            if (profileResponse?.avatar != null){
                               avatarImg.loadImage(profileResponse.avatar)
                                avatarBadgeImg.isVisible = false
                            }else{
                                avatarBadgeImg.isVisible = true
                                avatarImg.load(R.drawable.placeholder_user)
                            }
                        }
                    }

                    is NetworkRequestStatus.Error -> {
                        avatarLoading.isVisible = false
                        response.errorMessage?.let { root.showSnackBar(it, Snackbar.LENGTH_SHORT) }
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}