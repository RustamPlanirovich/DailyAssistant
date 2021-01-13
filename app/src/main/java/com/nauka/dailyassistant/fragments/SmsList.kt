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
import com.nauka.dailyassistant.Factory.SmsListFactory
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.adapters.SmsAdapters
import com.nauka.dailyassistant.databinding.FragmentSmsListBinding
import com.nauka.dailyassistant.ui.MainActivity
import com.nauka.dailyassistant.viewModels.SmsListViewModel


class SmsList : Fragment() {

    private lateinit var model: SmsListViewModel
    private lateinit var binding: FragmentSmsListBinding
    private val REQUEST_CODE_ASK_PERMISSIONS_SMS = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sms_list,
            container,
            false
        )



        if (ContextCompat.checkSelfPermission(
                context as Context,
                "android.permission.READ_SMS"
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            model =
                ViewModelProvider(this, SmsListFactory(activity?.applicationContext)).get(SmsListViewModel::class.java)
            binding.smsViewModel = model
            binding.lifecycleOwner = viewLifecycleOwner

            val adapter = SmsAdapters()
            binding.smsList.layoutManager = LinearLayoutManager(context)
            binding.smsList.adapter = adapter

            model.getSmsList().observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.refreshSmsList(it)
                }
            })

        } else {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                Array<String?>(3) { "android.permission.READ_SMS" },
                REQUEST_CODE_ASK_PERMISSIONS_SMS
            )
        }




        return binding.root
    }

}