package com.nauka.dailyassistant.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.ActivitySplashBinding
import com.nauka.dailyassistant.fragments.*
import com.nauka.dailyassistant.ui.MainActivity
import com.nauka.dailyassistant.viewModels.SplashViewModel
import kotlinx.coroutines.*
import java.io.File
import java.util.*


private val REQUEST_CODE_ASK_PERMISSIONS_CALL = 1
class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val job: Job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        /** Всегда темная тема приложения*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_ASK_PERMISSIONS_CALL)
        }else {
            write()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_ASK_PERMISSIONS_CALL)
        }else {
            write()
        }

        /** Подключаем биндинг к активити*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        /**привязываем владельца жизненного цикла для биндинга*/
        binding.lifecycleOwner = this
        /** Объявляем вьюмодел*/
        val model: SplashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        /** Привязываем вьюмодель к биндеру*/
        binding.splashViewModel = model

        /** запускаем новую корутину в основном потоке и после задержки в 3 секунды
         * запускаем ключаем видимость Fragments*/
        scope.launch(Dispatchers.Main) {
            delay(30)
            val intent = Intent(this@Splash, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS_CALL -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                write()
            }
        }
    }

    private fun write() {
        val dir = "${Environment.getExternalStorageDirectory()}/$packageName"
        File(dir).mkdirs()
        val file = "%1\$tY%1\$tm%1\$td%1\$tH%1\$tM%1\$tS.log".format(Date())
        File("$dir/$file").printWriter().use {
            it.println("text")
        }
    }

    /*При закрытии активити останавливаем Job'y и CoroutineScope*/
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        scope.cancel()
    }
}