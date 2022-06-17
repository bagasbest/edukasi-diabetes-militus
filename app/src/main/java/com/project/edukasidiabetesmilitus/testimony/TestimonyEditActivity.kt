package com.project.edukasidiabetesmilitus.testimony

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.edukasidiabetesmilitus.databinding.ActivityTestimonyEditBinding

class TestimonyEditActivity : AppCompatActivity() {

    private var _binding : ActivityTestimonyEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTestimonyEditBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}