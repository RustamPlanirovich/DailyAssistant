package com.nauka.dailyassistant.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
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
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        model = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.profileViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner


        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.registationButton.setOnClickListener {
            findNavController().navigate(R.id.action_global_login)
        }
    }
}

