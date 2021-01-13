package com.nauka.dailyassistant.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nauka.dailyassistant.Factory.NotificationFactory
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.databinding.FragmentNotificationListBinding
import com.nauka.dailyassistant.viewModels.NotificationViewModel
import org.w3c.dom.Text

//lateinit var texx: TextView
//lateinit var img: ImageView
//lateinit var img2: ImageView
class NotificationList : Fragment() {

    private lateinit var model: NotificationViewModel
    private lateinit var binding: FragmentNotificationListBinding
//    private var mReceiver: NotificationBroadcastReceiver? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notification_list,
            container,
            false
        )

        model = ViewModelProvider(this, NotificationFactory(activity?.applicationContext)).get(
            NotificationViewModel::class.java
        )
        binding.notificationViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner

//        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
//        if (intent.resolveActivity(activity?.packageManager!!) != null) {
//            startActivity(intent)
//        }
//
//        val filter = IntentFilter()
//        filter.addAction("com.nauka.dailyassistant.Services.NOTIFICATION_LISTENER_EXAMPLE")
//        activity?.registerReceiver(mReceiver, filter)
//
//        texx = binding.textf
//        img = binding.imageView
//        img2 = binding.imageView2

        return binding.root
    }

//    class NotificationBroadcastReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//
//            val temp =
//                intent.getStringExtra("notification_event") + "\\n" + texx.text
//            texx.setText(temp)
//
//            var byteArray = intent.getByteArrayExtra("notification_event1")
//            var bmp: Bitmap? = null
//            if (byteArray != null) {
//                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//
//            }
//            img2.setImageBitmap(bmp)
//
//            var byteArray1 = intent.getByteArrayExtra("notification_event2")
//            var bmp1: Bitmap? = null
//            if (byteArray1 != null) {
//                bmp1 = BitmapFactory.decodeByteArray(byteArray1, 0, byteArray1.size)
//
//            }
//            img.setImageBitmap(bmp1)
//        }
//    }
}

