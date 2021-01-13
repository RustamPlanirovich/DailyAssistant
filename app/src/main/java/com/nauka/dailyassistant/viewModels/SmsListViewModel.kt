package com.nauka.dailyassistant.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nauka.dailyassistant.data.smsData
import com.nauka.dailyassistant.util.SmsResponse

class SmsListViewModel(application: Context?) : ViewModel() {

    val sms: MutableLiveData<List<SmsResponse>> = MutableLiveData()

    init {
        sms.value = smsData(application).getSMS()

    }

    fun getSmsList() = sms

}