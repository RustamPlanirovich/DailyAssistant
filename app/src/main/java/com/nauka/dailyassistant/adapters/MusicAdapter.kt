package com.nauka.dailyassistant.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nauka.dailyassistant.databinding.MusicItemBinding
import com.nauka.dailyassistant.util.CustomClickListener
import com.nauka.dailyassistant.viewModels.MusicItemViewModel
import kotlinx.coroutines.*

class MusicAdapter(private val clickListener: CustomClickListener) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    var musicList: List<MusicItemViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MusicItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount() = musicList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(musicList[position], clickListener)

    inner class ViewHolder(val binding: MusicItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MusicItemViewModel, clickListener: CustomClickListener) {
            binding.mIviewModel = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    fun refreshMusic(musicList: List<MusicItemViewModel>) {
        this.musicList = musicList
        notifyDataSetChanged()
    }


}



