package com.project.edukasidiabetesmilitus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.project.edukasidiabetesmilitus.auth.LoginActivity
import com.project.edukasidiabetesmilitus.databinding.ActivityHomeBinding
import com.project.edukasidiabetesmilitus.image_media.ImageActivity
import com.project.edukasidiabetesmilitus.pdf_media.PDFActivity
import com.project.edukasidiabetesmilitus.testimony.TestimonyActivity
import com.project.edukasidiabetesmilitus.video_media.VideoActivity

class HomeActivity : AppCompatActivity() {

    private var _binding : ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        binding.apply {
            testimonyNext.setOnClickListener {
                startActivity(Intent(this@HomeActivity, TestimonyActivity::class.java))
            }

            loginBtn.setOnClickListener {
                if(FirebaseAuth.getInstance().currentUser != null) {
                    showLogoutDialog()
                } else {
                    startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                }
            }

            photoNext.setOnClickListener {
                startActivity(Intent(this@HomeActivity, ImageActivity::class.java))
            }

            pdfNext.setOnClickListener {
                startActivity(Intent(this@HomeActivity, PDFActivity::class.java))
            }

            videoNext.setOnClickListener {
                startActivity(Intent(this@HomeActivity, VideoActivity::class.java))
            }

        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout Admin")
            .setMessage("Apakah anda yakin ingin logout dari Admin ?")
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton("YA") { dialogInterface, _ ->
                dialogInterface.dismiss()
                FirebaseAuth.getInstance().signOut()
            }
            .setNegativeButton("TIDAK", null)
            .show()
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