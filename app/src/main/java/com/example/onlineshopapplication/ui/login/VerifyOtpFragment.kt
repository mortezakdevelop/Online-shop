package com.example.onlineshopapplication.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.databinding.FragmentVerifyOtpBinding
import com.example.onlineshopapplication.viewmodels.LoginViewModel


class VerifyOtpFragment : Fragment() {


    private lateinit var binding:FragmentVerifyOtpBinding
    private val viewmodel:LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVerifyOtpBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }

}