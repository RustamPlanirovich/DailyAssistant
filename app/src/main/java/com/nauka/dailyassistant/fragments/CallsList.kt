package com.nauka.dailyassistant.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nauka.dailyassistant.Factory.CallListFactory
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.adapters.CallAdapter
import com.nauka.dailyassistant.databinding.FragmentCallsListBinding
import com.nauka.dailyassistant.ui.MainActivity
import com.nauka.dailyassistant.viewModels.CallListViewModel


class CallsList : Fragment() {

    private lateinit var model: CallListViewModel
    private lateinit var binding: FragmentCallsListBinding
    private val REQUEST_CODE_ASK_PERMISSIONS_CALL = 124

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_calls_list,
            container,
            false
        )



        if (ContextCompat.checkSelfPermission(
                context as Context,
                "android.permission.READ_CALL_LOG"
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            model = ViewModelProvider(this, CallListFactory(activity?.applicationContext)).get(
                CallListViewModel::class.java
            )
            binding.callViewModel = model
            binding.lifecycleOwner = viewLifecycleOwner

            val adapter = CallAdapter()
            binding.callList.layoutManager = LinearLayoutManager(context)
            binding.callList.adapter = adapter

            model.getCallList().observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.refreshCallList(it)
                }
            })

            ActivityCompat.requestPermissions(
                context as MainActivity,
                Array<String?>(3) { "android.permission.READ_CONTACTS" },
                REQUEST_CODE_ASK_PERMISSIONS_CALL

            )


        } else {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                Array<String?>(3) { "android.permission.READ_CALL_LOG" },
                REQUEST_CODE_ASK_PERMISSIONS_CALL

            )

        }



        return binding.root
    }


}