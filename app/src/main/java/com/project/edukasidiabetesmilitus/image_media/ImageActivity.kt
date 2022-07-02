package com.project.edukasidiabetesmilitus.image_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.edukasidiabetesmilitus.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {

    private var _binding : ActivityImageBinding? = null
    private val binding get() = _binding!!
    private val imageList = ArrayList<ImageModel>()
    private var adapter : ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressCircular.visibility = View.VISIBLE
        ImageParseJson.parseJsonToImage(this, imageList)
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
        binding.rvImage.layoutManager = LinearLayoutManager(this)
        adapter = ImageAdapter(imageList)
        binding.rvImage.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}