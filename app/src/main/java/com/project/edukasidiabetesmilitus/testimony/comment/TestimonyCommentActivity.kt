package com.project.edukasidiabetesmilitus.testimony.comment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.project.edukasidiabetesmilitus.R
import com.project.edukasidiabetesmilitus.databinding.ActivityTestimonyCommentBinding
import java.text.SimpleDateFormat
import java.util.*

class TestimonyCommentActivity : AppCompatActivity() {

    private var _binding : ActivityTestimonyCommentBinding? = null
    private val binding get() = _binding!!
    private var postId = ""
    private var adapter: CommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTestimonyCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getStringExtra(POST_ID).toString()

        initRecyclerView()
        initViewModel()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.sendBtn.setOnClickListener {
           showCommentDialog()
        }


    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvComment.layoutManager = layoutManager
        adapter = CommentAdapter()
        binding.rvComment.adapter = adapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[CommentViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE
        viewModel.setListComment(postId)
        viewModel.getComment().observe(this) { commentList ->
            if (commentList.size > 0) {
                adapter?.setData(commentList)
                binding.noData.visibility = View.GONE
                updateCommentCount(commentList.size.toString())
            } else {
                binding.noData.visibility = View.VISIBLE
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun updateCommentCount(commentCount: String) {
        FirebaseFirestore
            .getInstance()
            .collection("testimony")
            .document(postId)
            .update("commentCount", commentCount)
    }

    private fun showCommentDialog() {
        val etName: TextInputEditText
        val etComment: TextInputEditText
        val confirmBtn: Button
        val pb: ProgressBar
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_comment)

        etName = dialog.findViewById(R.id.name)
        etComment = dialog.findViewById(R.id.comment)
        confirmBtn = dialog.findViewById(R.id.confirmBtn)
        pb = dialog.findViewById(R.id.progressBar)

        confirmBtn?.setOnClickListener {
            var name = etName.text.toString().trim()
            val comment = etComment.text.toString().trim()

            if(comment.isEmpty()) {
                Toast.makeText(this, "Maaf, komentar tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                val uid = System.currentTimeMillis().toString()
                if(name.isEmpty()) {
                    name = "user$uid"
                }
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("dd MMM yyyy, hh:mm", Locale.getDefault())
                val formattedDate = df.format(c.time)


                val data = mapOf(
                    "uid" to uid,
                    "comment" to comment,
                    "name" to name,
                    "date" to formattedDate
                )

                pb.visibility = View.VISIBLE
                FirebaseFirestore
                    .getInstance()
                    .collection("testimony")
                    .document(postId)
                    .collection("comment")
                    .document(uid)
                    .set(data)
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            pb.visibility = View.GONE
                            initRecyclerView()
                            initViewModel()
                            dialog.dismiss()
                            Toast.makeText(this, "Sukses menulis komentar", Toast.LENGTH_SHORT).show()
                        } else {
                            pb.visibility = View.GONE
                            dialog.dismiss()
                            Toast.makeText(this, "Gagal menulis komentar", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val POST_ID = "id"
    }
}