package com.nauka.dailyassistant.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.FragmentProfileBinding
import com.nauka.dailyassistant.ui.MainActivity
import com.nauka.dailyassistant.viewModels.ProfileViewModel


class ProfileFragment : Fragment() {

    private lateinit var model: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        model = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.profileViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner

        model.helloText = "Provide"

        binding.button.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}