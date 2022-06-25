package com.project.edukasidiabetesmilitus.video_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.edukasidiabetesmilitus.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    private var _binding : ActivityVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            backButton.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}