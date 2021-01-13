package com.nauka.dailyassistant.viewModels

import android.content.Context
import android.widget.SeekBar
import androidx.lifecycle.*
import com.nauka.dailyassistant.data.MusicData
import com.nauka.dailyassistant.data.Player
import kotlinx.coroutines.*


class MusicViewModel(val context: Context?) : ViewModel() {
    private val job: Job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    val player = Player()

    val seekMax: MediatorLiveData<Int> = MediatorLiveData()
    val progress: MediatorLiveData<Int> = MediatorLiveData()
    val name: MediatorLiveData<String> = MediatorLiveData()
    val pause: MediatorLiveData<Boolean> = MediatorLiveData()
    val time: MediatorLiveData<String> = MediatorLiveData()

    private val music: MutableLiveData<List<MusicItemViewModel>> = MutableLiveData()

    init {
        scope.launch {
            music.postValue(MusicData().getMusicList())
        }

        time.addSource(player.score) { time.value = it }
        pause.addSource(player.pause) { pause.value = it }
        name.addSource(player.name) { name.value = it }
        progress.addSource(player.progress) { progress.value = it }
        seekMax.addSource(player.seekMax) { seekMax.value = it }


        pause.value = true
    }


    fun getMusicList() = music

    fun replayTenSec() = player.replayTenSec()

    fun forwardTenSec() = player.forwardTenSec()

    fun pauseTrack() = player.pauseTrack()

    fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            player.player!!.seekTo(progress)
        }
    }

    override fun onCleared() {

    }
}