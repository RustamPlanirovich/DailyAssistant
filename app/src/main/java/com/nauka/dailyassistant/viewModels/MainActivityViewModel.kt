package com.nauka.dailyassistant.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {


    private var _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time


    fun setTime(returnTime: String) {
        _time.value = returnTime
    }
}