package com.example.onlineshopapplication.ui.login

import academy.nouri.storeapp.data.models.login.BodyLogin
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.databinding.FragmentLoginBinding
import com.example.onlineshopapplication.utils.PersianNumberInputFilter
import com.example.onlineshopapplication.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding

    private val viewModel:LoginViewModel by viewModels()

    @Inject
    lateinit var bodyLogin:BodyLogin
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
            edtMobilePhone.text?.filters = arrayOf(PersianNumberInputFilter(),InputFilter.LengthFilter(11))

            ivBottom.load(R.drawable.bg_circle)
            btnInputWithMobilePhone.setOnClickListener {

                viewModel.callLoginApi(bodyLogin = bodyLogin)
            }
        }
    }
}