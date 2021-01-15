package com.nauka.dailyassistant.splash

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.ActivitySplashBinding
import com.nauka.dailyassistant.fragments.*
import com.nauka.dailyassistant.ui.MainActivity
import com.nauka.dailyassistant.util.ForceUpdateChecker
import com.nauka.dailyassistant.viewModels.SplashViewModel
import kotlinx.coroutines.*
import java.util.*



private val REQUEST_CODE_ASK_PERMISSIONS_CALL = 1001

class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val job: Job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)



    override fun onCreate(savedInstanceState: Bundle?) {
        /** Всегда темная тема приложения*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)

        /** Подключаем биндинг к активити*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        /**привязываем владельца жизненного цикла для биндинга*/
        binding.lifecycleOwner = this
        /** Объявляем вьюмодел*/
        val model: SplashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        /** Привязываем вьюмодель к биндеру*/
        binding.splashViewModel = model
        checkWriteExternalStoragePermission()





        /** запускаем новую корутину в основном потоке и после задержки в 3 секунды
         * запускаем ключаем видимость Fragments*/
        scope.launch(Dispatchers.Main) {
            delay(30)
            val intent = Intent(this@Splash, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

    }

    /*При закрытии активити останавливаем Job'y и CoroutineScope*/
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        scope.cancel()
    }

    private fun checkWriteExternalStoragePermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // If we have permission than we can Start the Download the task
        } else {
            //  If we don't have permission than requesting  the permission
            requestWriteExternalStoragePermission()
        }
    }

    private fun requestWriteExternalStoragePermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_ASK_PERMISSIONS_CALL
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_ASK_PERMISSIONS_CALL
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS_CALL && grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {
            Toast.makeText(this, "Permission Not Granted.", Toast.LENGTH_SHORT).show()
        }
    }




}