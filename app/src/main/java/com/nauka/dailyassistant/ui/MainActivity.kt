package com.nauka.dailyassistant.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.ActivityMainBinding
import com.nauka.dailyassistant.fragments.*
import com.nauka.dailyassistant.util.CurrentTime
import com.nauka.dailyassistant.util.ForceUpdateChecker
import com.nauka.dailyassistant.util.ForceUpdateChecker.OnUpdateNeededListener
import com.nauka.dailyassistant.viewModels.MainActivityViewModel


private const val NUM_PAGES = 6

class MainActivity : AppCompatActivity(), OnUpdateNeededListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
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

        navController = Navigation.findNavController(this, R.id.nav_host)

        binding.currentTime.setOnClickListener {

        }

        ForceUpdateChecker().with(this)?.onUpdateNeeded(this)?.check()

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