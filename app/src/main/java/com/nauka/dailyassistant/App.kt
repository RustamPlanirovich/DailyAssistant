package com.nauka.dailyassistant

import android.app.Application
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nauka.dailyassistant.util.ForceUpdateChecker


class App: Application() {
    private val TAG = "Hello"

    override fun onCreate() {
        super.onCreate()
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        // set in-app defaults
        val remoteConfigDefaults: MutableMap<String, Any> = HashMap()
        remoteConfigDefaults.put(ForceUpdateChecker().KEY_UPDATE_REQUIRED, false)
        remoteConfigDefaults.put(ForceUpdateChecker().KEY_CURRENT_VERSION, "1.0.0")
        remoteConfigDefaults.put(ForceUpdateChecker().KEY_UPDATE_URL, "https://github.com/RustamPlanirovich/DailyAssistant/releases")
        remoteConfigDefaults.put(ForceUpdateChecker().KEY_RELEASE_NOTE,"No")

        firebaseRemoteConfig.setDefaultsAsync(remoteConfigDefaults)
        firebaseRemoteConfig.fetch(60) // fetch every minutes
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "remote config is fetched.")
                    firebaseRemoteConfig.activate()
                }
            }
    }
}