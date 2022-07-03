package com.project.edukasidiabetesmilitus.pdf_media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.edukasidiabetesmilitus.databinding.ActivityPdfactivityBinding
import com.project.edukasidiabetesmilitus.image_media.ImageParseJson
import com.project.edukasidiabetesmilitus.video_media.VideoAdapter
import com.project.edukasidiabetesmilitus.video_media.VideoModel

class PDFActivity : AppCompatActivity() {

    private var _binding : ActivityPdfactivityBinding? = null
    private val binding get() = _binding!!
    private val pdfList = ArrayList<PDFModel>()
    private var adapter : PDFAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPdfactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressCircular.visibility = View.VISIBLE
        ImageParseJson.parseJsonToPDF(this, pdfList)
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
        adapter = PDFAdapter(pdfList)
        binding.rvVideo.adapter = adapter
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}