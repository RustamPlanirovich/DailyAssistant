package com.nauka.dailyassistant.data

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nauka.dailyassistant.viewModels.MusicItemViewModel
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit


class Player() {


    var player: MediaPlayer? = null
    var seekMax: MutableLiveData<Int> = MutableLiveData()
    val progress: MutableLiveData<Int> = MutableLiveData()
    val pause: MutableLiveData<Boolean> = MutableLiveData()

    private var _time: MutableLiveData<String> = MutableLiveData()
    val score: LiveData<String>
        get() = _time




    val name: MutableLiveData<String> = MutableLiveData()
    lateinit var musicScope:CoroutineScope


    fun playTrack(view: MusicItemViewModel?) {
        val timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("Time", score.value.toString())
            }
            override fun onFinish() {}
        }
        timer.start()
        try {
            player = MediaPlayer()
            if (player?.isPlaying == false) {
                player?.setDataSource(view?.comments)
                player?.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                player?.prepare()
                player?.start()
                name.value = view?.title
                pause.value = false
            }
            player?.setOnPreparedListener {
                seekMax.postValue(player?.duration)
                musicScope = CoroutineScope(Dispatchers.IO)
                if (player?.currentPosition != seekMax.value) {
                    musicScope.launch {
                        while (true) {
                            delay(1000)
                            progress.postValue(player?.currentPosition)
                            val diff = player?.duration!! - player?.currentPosition!!.toLong()
                            _time.postValue(convertMillis1(diff))
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.i("Exception", "Exception in streaming mediaplayer e = $e")
        }
    }


    fun pauseTrack() {
        if (player?.isPlaying == true) {
            player?.pause()
            pause.value = true
        } else {
            if (player != null){
                val position: Int = progress.value as Int
                player?.seekTo(position)
                player?.start()
                pause.value = false
            }
        }
    }

    fun stopTrack() {
        if (player != null) {
            musicScope.cancel()
            player?.stop()
            player?.release()
            player = null

        }
    }


    @SuppressLint("DefaultLocale")
    private fun convertMillis1(milliseconds2: Long): kotlin.String {
        val sdfsd = java.lang.String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(milliseconds2) % 24,
            TimeUnit.MILLISECONDS.toMinutes(milliseconds2) % 60,
            TimeUnit.MILLISECONDS.toSeconds(milliseconds2) % 60
        )
        return sdfsd
    }

    fun replayTenSec() {
        player?.seekTo(player?.currentPosition!!.minus(10000))
    }

    fun forwardTenSec() {
        player?.seekTo(player?.currentPosition!!.plus(10000))
    }

}