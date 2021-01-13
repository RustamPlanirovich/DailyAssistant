package com.nauka.dailyassistant.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.ActivityMainBinding
import com.nauka.dailyassistant.fragments.*
import com.nauka.dailyassistant.util.CurrentTime
import com.nauka.dailyassistant.viewModels.MainActivityViewModel

private const val NUM_PAGES = 6

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var model: MainActivityViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this


        val currentTime: LiveData<String> = CurrentTime().currentTime()
        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.mainViewModel = model

        navController = Navigation.findNavController(this, R.id.nav_host)

        binding.currentTime.setOnClickListener {

        }


        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        binding.pager.adapter = pagerAdapter
        binding.pager.currentItem = 1

        currentTime.observe(this, {
            model.setTime(it)
        })

    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment {

            return when (position) {
                0 -> NotificationList()
                1 -> ProgrammingMusic()
                2 -> SmsList()
                3 -> CallsList()
                4 -> Links()
                5 -> ProfileFragment()
                else -> ProgrammingMusic()
            }
        }
    }


    override fun onBackPressed() {
        //super.onBackPressed()
        if (binding.pager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            binding.pager.currentItem = binding.pager.currentItem + 1
        } else if (binding.pager.currentItem > 1) {
            // Otherwise, select the previous step.
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }
}