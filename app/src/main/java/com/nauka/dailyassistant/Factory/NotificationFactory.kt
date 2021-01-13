package com.nauka.dailyassistant.Factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nauka.dailyassistant.viewModels.NotificationViewModel

class NotificationFactory(val context: Context?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotificationViewModel(context) as T
    }
}