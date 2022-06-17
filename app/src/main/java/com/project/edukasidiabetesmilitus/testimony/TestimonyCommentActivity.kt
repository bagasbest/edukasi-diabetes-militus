package com.project.edukasidiabetesmilitus.testimony

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.edukasidiabetesmilitus.databinding.ActivityTestimonyCommentBinding

class TestimonyCommentActivity : AppCompatActivity() {

    private var _binding : ActivityTestimonyCommentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTestimonyCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val POST_ID = "id"
    }
}