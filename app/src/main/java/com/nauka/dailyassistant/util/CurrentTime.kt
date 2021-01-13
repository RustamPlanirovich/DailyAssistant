package com.nauka.dailyassistant.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class CurrentTime() {

    private val job: Job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun currentTime(): LiveData<String> {
        val retTime: MutableLiveData<String> = MutableLiveData()
        scope.launch {
            while (true) {
                delay(1000)
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm").format(c.time)
                retTime.postValue(sdf)
            }
        }
        return retTime
    }
}