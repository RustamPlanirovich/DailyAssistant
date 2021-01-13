package com.nauka.dailyassistant.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.FragmentLinksBinding
import com.nauka.dailyassistant.viewModels.LinkViewModel


class Links : Fragment() {

    private lateinit var model: LinkViewModel
    private lateinit var binding: FragmentLinksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_links,
            container,
            false
        )

        model = ViewModelProvider(this).get(LinkViewModel::class.java)
        binding.linkViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}