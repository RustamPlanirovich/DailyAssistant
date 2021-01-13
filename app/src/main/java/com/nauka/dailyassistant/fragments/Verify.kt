package com.nauka.dailyassistant.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.FragmentVerifyBinding
import com.nauka.dailyassistant.viewModels.VerifyViewModel


class Verify : Fragment() {

    private lateinit var model: VerifyViewModel
    private lateinit var binding: FragmentVerifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_verify,
            container,
            false
        )

        model = ViewModelProvider(this).get(VerifyViewModel::class.java)
        binding.verifyViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}