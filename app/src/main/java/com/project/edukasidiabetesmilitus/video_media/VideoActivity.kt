package com.project.edukasidiabetesmilitus.video_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.edukasidiabetesmilitus.databinding.ActivityVideoBinding
import com.project.edukasidiabetesmilitus.image_media.ImageParseJson

class VideoActivity : AppCompatActivity() {

    private var _binding : ActivityVideoBinding? = null
    private val binding get() = _binding!!
    private val videoList = ArrayList<VideoModel>()
    private var adapter : VideoAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressCircular.visibility = View.VISIBLE
        ImageParseJson.parseJsonToVideo(this, videoList)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressCircular.visibility = View.GONE
            initRecyclerView()
        }, 2000)

        binding.apply {
            backButton.setOnClickListener {
                onBackPressed()
            }
        }


    }

    private fun initRecyclerView() {
        binding.rvVideo.layoutManager = LinearLayoutManager(this)
        adapter = VideoAdapter(videoList)
        binding.rvVideo.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}