package com.project.edukasidiabetesmilitus.image_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.edukasidiabetesmilitus.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {

    private var _binding : ActivityImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageBinding.inflate(layoutInflater)
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