package com.nauka.dailyassistant.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.FragmentLoginBinding
import com.nauka.dailyassistant.ui.MainActivity
import com.nauka.dailyassistant.viewModels.LoginViewModel


class Login : Fragment() {

    private lateinit var model: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        model = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.sendButton.setOnClickListener {

        }

    }

}