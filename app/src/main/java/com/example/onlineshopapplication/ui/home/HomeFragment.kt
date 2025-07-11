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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import coil.load
import coil.request.CachePolicy
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.data.models.home.BannerResponse
import com.example.onlineshopapplication.data.models.home.DiscountResponse
import com.example.onlineshopapplication.data.models.home.DiscountResponse.*
import com.example.onlineshopapplication.databinding.FragmentHomeBinding
import com.example.onlineshopapplication.databinding.FragmentVerifyOtpBinding
import com.example.onlineshopapplication.ui.home.adapter.BannerAdapter
import com.example.onlineshopapplication.ui.home.adapter.DiscountAdapter
import com.example.onlineshopapplication.ui.login.LoginFragmentDirections
import com.example.onlineshopapplication.utils.NetworkMessagesResponse
import com.example.onlineshopapplication.utils.NetworkRequestStatus
import com.example.onlineshopapplication.utils.extensions.enableLoading
import com.example.onlineshopapplication.utils.extensions.isVisible
import com.example.onlineshopapplication.utils.extensions.loadImage
import com.example.onlineshopapplication.utils.extensions.showSnackBar
import com.example.onlineshopapplication.utils.isCalledVerify
import com.example.onlineshopapplication.viewmodels.HomeViewModel
import com.example.onlineshopapplication.viewmodels.ProfileViewModel
import com.faltenreich.skeletonlayout.SkeletonConfig
import com.faltenreich.skeletonlayout.createSkeleton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var bannerAdapter: BannerAdapter

    @Inject
    lateinit var discountAdapter:DiscountAdapter

    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val pagerSnapHelper by lazy { PagerSnapHelper() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        loadProfileDetailData()
        loadBannerData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.avatarImg.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
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
                            if (profileResponse?.avatar != null) {
                                avatarImg.loadImage(profileResponse.avatar)
                                avatarBadgeImg.isVisible = false
                            } else {
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

    private fun loadBannerData() {
        binding.apply {
            homeViewModel.bannerList.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequestStatus.Loading -> {
                        bannerLoading.isVisible(true, bannerList)
                    }

                    is NetworkRequestStatus.Success -> {
                        bannerLoading.isVisible(false, bannerList)
                        response.data?.let { data ->
                            if (data.isNotEmpty()) {
                                initBannerRecycler(data)
                            }
                        }
                    }

                    is NetworkRequestStatus.Error -> {
                        avatarLoading.isVisible(false, bannerList)
                        response.errorMessage?.let { root.showSnackBar(it, Snackbar.LENGTH_SHORT) }
                    }
                }
            }
        }
    }

    private fun loadDiscountData() {
        binding.apply {
            homeViewModel.discountList.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequestStatus.Loading -> {
                        discountList.showSkeleton()
                    }

                    is NetworkRequestStatus.Success -> {
                        discountList.showOriginal()
                        response.data?.let {
                            if (it.isNotEmpty()) {

                            }
                        }
                    }

                    is NetworkRequestStatus.Error -> {
                        discountList.showOriginal()
                    }
                }
            }
        }
    }

    private fun initBannerRecycler(data: List<BannerResponse.BannerResponseItem>) {
        bannerAdapter.setData(data)
        binding.bannerList.apply {
            adapter = bannerAdapter
            set3DItem(true)
            setAlpha(true)
            setInfinite(false)
        }

        bannerAdapter.setOnItemClickListener {

        }

        pagerSnapHelper.attachToRecyclerView(binding.bannerList)
        binding.bannerIndicator.attachToRecyclerView(binding.bannerList, pagerSnapHelper)
    }

    private fun initDiscountRecycler(data: List<ResponseDiscountItem>) {
        discountAdapter.setData(data)
        binding.discountList.apply {
        }

        discountAdapter.setOnItemClickListener {

        }

        pagerSnapHelper.attachToRecyclerView(binding.bannerList)
        binding.bannerIndicator.attachToRecyclerView(binding.bannerList, pagerSnapHelper)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}