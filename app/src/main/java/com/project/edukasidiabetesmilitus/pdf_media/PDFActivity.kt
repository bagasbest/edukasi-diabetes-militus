package com.project.edukasidiabetesmilitus.pdf_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.edukasidiabetesmilitus.databinding.ActivityPdfactivityBinding

class PDFActivity : AppCompatActivity() {

    private var _binding : ActivityPdfactivityBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPdfactivityBinding.inflate(layoutInflater)
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