package com.nauka.dailyassistant.util

import android.content.Context
import com.nauka.dailyassistant.viewModels.MusicItemViewModel
import com.nauka.dailyassistant.viewModels.MusicViewModel

class CustomClickListener(val clickListener: (view: MusicItemViewModel?) -> Unit) {
    fun button(view: MusicItemViewModel?) = clickListener(view)
}