package com.nauka.dailyassistant.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eukaprotech.xmlprocessor.RssXmlProcessor
import com.eukaprotech.xmlprocessor.RssXmlToJSONArrayHandler
import com.nauka.dailyassistant.util.MusicResponse
import com.nauka.dailyassistant.viewModels.MusicItemViewModel
import com.nauka.dailyassistant.viewModels.MusicViewModel
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class MusicData() {

    val dataSet: ArrayList<MusicItemViewModel> = ArrayList()


    fun getMusicList(): ArrayList<MusicItemViewModel> {
        val result = URL("https://www.musicforprogramming.net/rss.php").readText()

        val entryTag = "item"
        val entryKeys = ArrayList<String>()
        entryKeys.add("title")
        entryKeys.add("duration")
        entryKeys.add("comments")
        val xmlProcessor = RssXmlProcessor(entryTag, entryKeys)
        xmlProcessor.execute(result,
            object : RssXmlToJSONArrayHandler {
                override fun onStart() {}
                override fun onSuccess(items: JSONArray) {
                    //consuming the processed items using the same keys
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        dataSet.add(
                            MusicItemViewModel(
                                item.getString("title"),
                                item.getString("duration"),
                                item.getString("comments")
                            )
                        )
                    }
                }

                override fun onFail(ex: java.lang.Exception) {}

                override fun onComplete() {
                }
            })
        return dataSet
    }
}