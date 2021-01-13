package com.nauka.dailyassistant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nauka.dailyassistant.Factory.MusicFactory
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.adapters.MusicAdapter
import com.nauka.dailyassistant.databinding.FragmentProgrammingMusicBinding
import com.nauka.dailyassistant.util.CustomClickListener
import com.nauka.dailyassistant.viewModels.MusicViewModel
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.*


class ProgrammingMusic : Fragment() {

    private lateinit var binding: FragmentProgrammingMusicBinding
    private lateinit var model: MusicViewModel
    private lateinit var adapter: MusicAdapter
    private val job: Job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    lateinit var blurView: BlurView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_programming_music,
            container,
            false
        )



        model = ViewModelProvider(
            this,
            MusicFactory(activity?.applicationContext)
        ).get(MusicViewModel::class.java)

        binding.musicViewModel = model
        binding.lifecycleOwner = viewLifecycleOwner


        adapter = MusicAdapter(CustomClickListener { view ->
            if (view?.title != model.name.value) {
                model.player.stopTrack()
                if (model.player.player == null) {
                    model.player.playTrack(view)
                }
                if (!binding.blurLayout.isVisible) {
                    binding.blurLayout.visibility = View.VISIBLE

                    binding.musicRV.findViewHolderForAdapterPosition(binding.musicRV.id)?.itemView?.setBackgroundResource(
                        R.color.red
                    )
                }
            }
        })


        binding.musicRV.layoutManager = LinearLayoutManager(context)
        binding.musicRV.adapter = adapter

        blurView = binding.blurLayout


        model.seekMax.observe(viewLifecycleOwner,{})
        model.progress.observe(viewLifecycleOwner,{})
        model.name.observe(viewLifecycleOwner,{})
        model.pause.observe(viewLifecycleOwner,{})
        model.time.observe(viewLifecycleOwner,{})




        blur()


        binding.musicRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(binding.musicRV, dx, dy)
                if (dy > 2 && binding.musicRV.isVisible) {
                    binding.blurLayout.visibility = View.GONE
                } else if (dy < 0) {
                    binding.blurLayout.visibility = View.VISIBLE
                }
            }
        })

        binding.blurLayout.clipToOutline = true



        scope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                model.getMusicList().observe(viewLifecycleOwner, Observer {
                    it.let {
                        adapter.refreshMusic(it)
                        binding.progressBar.visibility = View.GONE

                    }
                })
            }
        }


        return binding.root
    }

    fun blur() {
        val radius = 10f

        val decorView: View = activity?.getWindow()!!.getDecorView()

        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background

        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(context))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setBlurEnabled(true)
    }


}