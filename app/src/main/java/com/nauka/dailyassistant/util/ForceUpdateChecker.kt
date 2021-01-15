package com.nauka.dailyassistant.util

import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


class ForceUpdateChecker() {

    private val TAG = "Hello"

    val KEY_UPDATE_REQUIRED = "force_update_required"
    val KEY_CURRENT_VERSION = "force_update_current_version"
    val KEY_UPDATE_URL = "force_update_store_url"
    val KEY_RELEASE_NOTE = "release_note"

    private var onUpdateNeededListener: OnUpdateNeededListener? = null
    private var context: Context? = null

    interface OnUpdateNeededListener {
        fun onUpdateNeeded(updateUrl: String?)
    }

    fun with(context: Context): Builder? {
        return Builder(context)
    }

    constructor(context: Context,
        onUpdateNeededListener: OnUpdateNeededListener?) : this() {
        this.context = context
        this.onUpdateNeededListener = onUpdateNeededListener
    }
//


    fun check() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
            val currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION)
            val appVersion = getAppVersion(context)
            val updateUrl = remoteConfig.getString(KEY_UPDATE_URL)
            val releaseNote = remoteConfig.getString(KEY_RELEASE_NOTE)
            if (!TextUtils.equals(currentVersion, appVersion)
                && onUpdateNeededListener != null
            ) {
                onUpdateNeededListener!!.onUpdateNeeded(updateUrl)
                Log.i("Hello", "${remoteConfig.getString(KEY_CURRENT_VERSION)}" + " ${remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)}" + " ${remoteConfig.getString(KEY_RELEASE_NOTE)}")
            }
        }
    }

    private fun getAppVersion(context: Context?): String {
        var result = ""
        try {
            result = context?.getPackageManager()
                ?.getPackageInfo(context.getPackageName(), 0)!!.versionName
            result = result.replace("[a-zA-Z]|-".toRegex(), "")
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, e.message!!)
        }
        return result
    }

    class Builder(context: Context) {
        val context: Context
        private var onUpdateNeededListener: OnUpdateNeededListener? = null

        fun onUpdateNeeded(onUpdateNeededListener: OnUpdateNeededListener?): Builder {
            this.onUpdateNeededListener = onUpdateNeededListener
            return this
        }

        fun build(): ForceUpdateChecker {
           return ForceUpdateChecker(context, onUpdateNeededListener)
        }

        fun check(): ForceUpdateChecker {
            val forceUpdateChecker = build()
            forceUpdateChecker.check()
            return forceUpdateChecker
        }

        init {
            this.context = context
        }
    }
}