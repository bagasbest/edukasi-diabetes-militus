package com.project.edukasidiabetesmilitus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.project.edukasidiabetesmilitus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(resources.getString(R.string.background))
            .into(binding.background)

        Glide.with(this)
            .load(R.drawable.logo)
            .into(binding.logo)

        Glide.with(this)
            .load(R.drawable.karanganyar)
            .into(binding.karanganyar)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
        }, 5000)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}