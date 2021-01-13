package com.nauka.dailyassistant.Services

import android.annotation.SuppressLint
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Parcelable
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import java.io.ByteArrayOutputStream

class NLService : NotificationListenerService() {

//    private val TAG = "hELLO"
//    private lateinit var mReceiver: NLServiceReceiver
//
//    override fun onCreate() {
//        super.onCreate()
//        mReceiver = NLServiceReceiver()
//        val filter = IntentFilter()
//        filter.addAction("com.nauka.dailyassistant.Services.NOTIFICATION_LISTENER_SERVICE_EXAMPLE")
//        registerReceiver(mReceiver, filter)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(mReceiver)
//    }
//
//    @SuppressLint("UseCompatLoadingForDrawables")
//    override fun onNotificationPosted(sbn: StatusBarNotification) {
//        val mNotification = sbn.notification
//        val extras: Bundle = mNotification.extras
//        val notificationTitle: String? = extras.getString(Notification.EXTRA_TITLE)
//        val notificationSub: String? = extras.getString(Notification.EXTRA_TEXT)
//
//        val id: Bitmap = (extras.getParcelable<Parcelable>(Notification.EXTRA_LARGE_ICON) as Bitmap?)!!
//
//        val id1 = extras.getInt(Notification.EXTRA_SMALL_ICON)
//
//        val pack = sbn.packageName
//        var remotePackageContext: Context? = null
//
//        var bmp: Bitmap? = null
//        try {
//            remotePackageContext = applicationContext.createPackageContext(pack, 0)
//            val icon = remotePackageContext.resources.getDrawable(id1)
//            if (icon != null) {
//                bmp = (icon as BitmapDrawable).bitmap
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        Log.i(TAG, "onNotificationPosted")
//        Log.i(
//            TAG, "ID :" + sbn.id + "\\t" + sbn.notification.tickerText + "\\t" +
//                    sbn.packageName + "\\t" +
//                    sbn.postTime + "\\t" +
//                    notificationTitle + "\\t" +
//                    notificationSub
//        )
//        val intent = Intent("com.nauka.dailyassistant.Services.NOTIFICATION_LISTENER_EXAMPLE")
//        intent.putExtra(
//            "notification_event", "onNotificationPosted:\\n" + sbn.id + "\\t" +
//                    sbn.notification.tickerText + "\\t" +
//                    sbn.packageName + "\\t" +
//                    sbn.postTime + "\\t" +
//                    notificationTitle + "\\t" +
//                    notificationSub
//        )
//
//        if (id != null) {
//            val stream = ByteArrayOutputStream()
//            id.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val byteArray = stream.toByteArray()
//            intent.putExtra("notification_event1", byteArray)
//            Log.i(TAG, "Hekko$byteArray")
//        }
//
//        if (bmp != null) {
//            val stream1 = ByteArrayOutputStream()
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream1)
//            val byteArray1 = stream1.toByteArray()
//            intent.putExtra("notification_event2", byteArray1)
//            Log.i(TAG, "Hekko1$byteArray1")
//        }
//
//        sendBroadcast(intent)
//    }
//
//    override fun onNotificationRemoved(sbn: StatusBarNotification) {
//        val mNotification = sbn.notification
//        val extras = mNotification.extras
//        val notificationTitle = extras.getString(Notification.EXTRA_TITLE)
//        val notificationSub = extras.getString(Notification.EXTRA_TEXT)
//        Log.i(TAG, "onNOtificationRemoved")
//        Log.i(TAG, "ID :" + sbn.id + "\\t" + sbn.notification.tickerText + "\\t" + sbn.packageName)
//        val intent = Intent("com.nauka.dailyassistant.Services.NOTIFICATION_LISTENER_EXAMPLE")
//        intent.putExtra(
//            "notification_event", "onNotificationRemoved:\\n" + sbn.id + "\\t" +
//                    sbn.notification.tickerText + "\\t" +
//                    sbn.packageName + "\\t" +
//                    sbn.postTime + "\\t" +
//                    notificationTitle + "\\t" +
//                    notificationSub
//        )
//        sendBroadcast(intent)
//    }
//
//    inner class NLServiceReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            if (intent.getStringExtra("command") == "clearall") {
//                this@NLService.cancelAllNotifications()
//            } else if (intent.getStringExtra("command") == "list") {
//                val notificationIntent = Intent("com.nauka.dailyassistant.Services.NOTIFICATION_LISTENER_EXAMPLE")
//                notificationIntent.putExtra("notification_event", "=======")
//                sendBroadcast(notificationIntent)
//                var i = 1
//                for (sbn in this@NLService.getActiveNotifications()) {
//                    val infoIntent = Intent("com.nauka.dailyassistant.Services.NOTIFICATION_LISTENER_EXAMPLE")
//                    infoIntent.putExtra(
//                        "notification_event",
//                        i.toString() + " " + sbn.packageName + "\\n"
//                    )
//                    sendBroadcast(infoIntent)
//                    i++
//                }
//                val listIntent = Intent("com.nauka.dailyassistant.Services.NOTIFICATION_LISTENER_EXAMPLE")
//                listIntent.putExtra("notification_event", "Notification List")
//                sendBroadcast(listIntent)
//            }
//        }
//    }
}