package com.nauka.dailyassistant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.adapters.*
import com.nauka.dailyassistant.fragments.titleFragments.*
import com.nauka.dailyassistant.util.HingeTransformation


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val fragmentList = arrayListOf<Fragment>(
            GeneralFragment(),
            CallFragment(),
            LinksFragment(),
            MusicFragment(),
            NotificationFragment(),
            PlansFragment(),
            ProfileSFragment(),
            PurchasesFragment(),
            SmsFragment(),
            ThoughtsFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val hingeTransformation = HingeTransformation()
        val pager = view.findViewById<ViewPager2>(R.id.pager)
        pager.adapter = adapter
        pager.setPageTransformer(hingeTransformation)
        return view
    }


}