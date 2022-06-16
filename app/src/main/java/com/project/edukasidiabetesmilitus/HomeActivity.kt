package com.project.edukasidiabetesmilitus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.project.edukasidiabetesmilitus.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private var _binding : ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        Glide.with(this)
            .load(R.drawable.photo)
            .into(binding.photo)

        Glide.with(this)
            .load(R.drawable.pdf)
            .into(binding.pdf)

        Glide.with(this)
            .load(R.drawable.video)
            .into(binding.video)

        Glide.with(this)
            .load(R.drawable.discussion)
            .into(binding.testimony)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}