package com.nauka.dailyassistant.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(
    list: ArrayList<Fragment>,
    fragment: FragmentManager,
    lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment, lifecycle) {

    val fragmentList = list

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}