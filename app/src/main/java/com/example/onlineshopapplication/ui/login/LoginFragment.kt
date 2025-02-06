package com.example.onlineshopapplication.ui.login

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.databinding.FragmentLoginBinding
import com.example.onlineshopapplication.utils.PersianNumberInputFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            phoneEdt.text?.filters = arrayOf(PersianNumberInputFilter(),InputFilter.LengthFilter(11))
        }
    }
}