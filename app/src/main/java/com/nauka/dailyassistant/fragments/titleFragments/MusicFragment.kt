package com.nauka.dailyassistant.fragments.titleFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.nauka.dailyassistant.R



class MusicFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_music, container, false)

        val click = view.findViewById<TextView>(R.id.text)
//        click.setOnClickListener {
//            findNavController().navigate(R.id.action_global_programmingMusic)
//        }
        return view
    }


}