package com.example.onlineshopapplication.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.utils.NetworkChecker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var networkChecker: NetworkChecker

    var  isNetworkAvailable = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            networkChecker.checkNetwork().collect{
                isNetworkAvailable = it
                if (!it){
                    Toast.makeText(requireContext(), R.string.check_network, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}