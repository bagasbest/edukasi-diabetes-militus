package com.project.edukasidiabetesmilitus.image_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.project.edukasidiabetesmilitus.databinding.ActivityImageDetailBinding

class ImageDetailActivity : AppCompatActivity() {

    private var _binding : ActivityImageDetailBinding? = null
    private val binding  get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra(IMAGE)
        Glide.with(this)
            .load(image)
            .into(binding.image)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val IMAGE = "image"
    }
}