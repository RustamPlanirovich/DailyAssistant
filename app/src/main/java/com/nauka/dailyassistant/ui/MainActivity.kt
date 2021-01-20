package com.nauka.dailyassistant.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.ActivityMainBinding
import com.nauka.dailyassistant.util.CurrentTime
import com.nauka.dailyassistant.util.ForceUpdateChecker.OnUpdateNeededListener
import com.nauka.dailyassistant.viewModels.MainActivityViewModel


private const val NUM_PAGES = 6

class MainActivity : AppCompatActivity(), OnUpdateNeededListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var model: MainActivityViewModel
    lateinit var context: Context


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

        /**Проверка новой версии с помощью Firebase Remote Config
         *обслуживаеющий классы: ForceUpdateChecker и App*/
        //ForceUpdateChecker().with(this)?.onUpdateNeeded(this)?.check()

        currentTime.observe(this, {
            model.setTime(it)
        })

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