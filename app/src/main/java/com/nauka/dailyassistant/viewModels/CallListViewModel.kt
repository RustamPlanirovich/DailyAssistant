package com.nauka.dailyassistant.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nauka.dailyassistant.data.callData
import com.nauka.dailyassistant.util.CallResponse

class CallListViewModel(context: Context?) : ViewModel() {

    val call: MutableLiveData<List<CallResponse>> = MutableLiveData()

    init {
        call.value = callData(context).getCallList()
    }

    fun getCallList() = call
}