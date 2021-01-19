package com.nauka.dailyassistant.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.ActivityMainBinding
import com.nauka.dailyassistant.fragments.*
import com.nauka.dailyassistant.util.CurrentTime
import com.nauka.dailyassistant.util.ForceUpdateChecker.OnUpdateNeededListener
import com.nauka.dailyassistant.viewModels.MainActivityViewModel
import nl.joery.animatedbottombar.AnimatedBottomBar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity(), OnUpdateNeededListener {
    var NUM_PAGES = 100
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var model: MainActivityViewModel
    lateinit var context: Context

    @SuppressLint("StringFormatInvalid")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        context = this


        val currentTime: LiveData<String> = CurrentTime().currentTime()
        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.mainViewModel = model

        navController = Navigation.findNavController(this, R.id.fragment)


        binding.currentTime.setOnClickListener {

        }

        /**Проверка новой версии с помощью Firebase Remote Config
         *обслуживаеющий классы: ForceUpdateChecker и App*/
        //ForceUpdateChecker().with(this)?.onUpdateNeeded(this)?.check()


        //val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        // binding.pager.adapter = pagerAdapter
        //binding.pager.currentItem = 1

        binding.bottomBar.selectTabAt(0, true)


        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newIndex) {
                    0 -> navController.navigate(R.id.login2)
                    1 -> navController.navigate(R.id.programmingMusic)
                }
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                when (index) {
                    //0 -> Login().onStop()
                }
            }
        })
        currentTime.observe(this, {
            model.setTime(it)
        })

    }


//    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) :
//        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//        override fun getCount(): Int = NUM_PAGES
//
//        override fun getItem(position: Int): Fragment {
//
//            return when (position) {
//                0 -> NotificationList()
//                1 -> ProgrammingMusic()
//                2 -> SmsList()
//                3 -> CallsList()
//                4 -> Links()
//                5 -> ProfileFragment()
//                6 -> Login()
//                else -> ProgrammingMusic()
//            }
//        }
//
//        override fun getItemPosition(`object`: Any): Int {
//            return super.getItemPosition(`object`)
//        }
//
//    }


//    override fun onBackPressed() {
//        //super.onBackPressed()
//        if (binding.pager.currentItem == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            binding.pager.currentItem = binding.pager.currentItem + 1
//        } else if (binding.pager.currentItem > 1) {
//            // Otherwise, select the previous step.
//            binding.pager.currentItem = binding.pager.currentItem - 1
//        }
//    }

    override fun onUpdateNeeded(updateUrl: String?) {
        val KEY_CURRENT_VERSION = "force_update_current_version"
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION).split(".")

        val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(this)
            .setTitle("Доступна новая версия" + currentVersion)
            .setMessage(
                "Пожалуйста, обновите приложение до новой версии, в противном " +
                        "случаем работа приложения будет осановлена"
            )
            .setPositiveButton("Обновить",
                DialogInterface.OnClickListener { dialog, which -> redirectStore(updateUrl) })
            .setNegativeButton("Нет, спасибо",
                DialogInterface.OnClickListener { dialog, which -> finish() }).create()
        dialog?.show()
    }

    private fun redirectStore(updateUrl: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}