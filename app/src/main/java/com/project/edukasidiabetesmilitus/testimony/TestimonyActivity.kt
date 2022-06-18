package com.project.edukasidiabetesmilitus.testimony

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.project.edukasidiabetesmilitus.databinding.ActivityTestimonyBinding

class TestimonyActivity : AppCompatActivity() {

    private var _binding : ActivityTestimonyBinding ? = null
    private val binding get() = _binding!!
    private var adapter : TestimonyAdapter? = null
    private var role = ""

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        initViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTestimonyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.addbtn.setOnClickListener {
            startActivity(Intent(this, TestimonyAddActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        role = if(FirebaseAuth.getInstance().currentUser != null) {
            "admin"
        } else {
            "user"
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvTestimony.layoutManager = layoutManager
        adapter = TestimonyAdapter(role)
        binding.rvTestimony.adapter = adapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[TestimonyViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE
        viewModel.setListTestimony()
        viewModel.getTestimony().observe(this) { testimonyList ->
            if (testimonyList.size > 0) {
                adapter?.setData(testimonyList)
                binding.noData.visibility = View.GONE
            } else {
                binding.noData.visibility = View.VISIBLE
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}