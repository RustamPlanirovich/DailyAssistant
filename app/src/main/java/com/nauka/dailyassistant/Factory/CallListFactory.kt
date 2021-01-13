package com.nauka.dailyassistant.Factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nauka.dailyassistant.viewModels.CallListViewModel

class CallListFactory(val context: Context?) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CallListViewModel(context) as T
    }
}