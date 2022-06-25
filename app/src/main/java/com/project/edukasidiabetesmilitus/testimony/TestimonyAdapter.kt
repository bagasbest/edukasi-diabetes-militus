package com.project.edukasidiabetesmilitus.testimony

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.project.edukasidiabetesmilitus.R
import com.project.edukasidiabetesmilitus.databinding.ItemTestimonyBinding
import com.project.edukasidiabetesmilitus.testimony.comment.TestimonyCommentActivity

class TestimonyAdapter(private val role: String) : RecyclerView.Adapter<TestimonyAdapter.ViewHolder>() {

    private val testimonyList = ArrayList<TestimonyModel>()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<TestimonyModel>) {
        testimonyList.clear()
        testimonyList.addAll(items)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: ItemTestimonyBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: TestimonyModel, testimonyList: ArrayList<TestimonyModel>) {
            with(binding) {

                name.text = model.name
                status.text = model.status
                title.text = model.title
                description.text = model.description
                commentCnt.text = "${model.commentCount} Balasan"

                if(role == "admin") {
                    delete.visibility = View.VISIBLE
                }

                if(model.avatar != ""){
                    Glide.with(itemView.context)
                        .load(model.avatar)
                        .into(avatar)
                }

                if(model.image != "") {
                    image.visibility = View.VISIBLE
                    Glide.with(itemView.context)
                        .load(model.image)
                        .into(image)
                } else {
                    image.visibility = View.GONE
                }



                comment.setOnClickListener {
                    val intent = Intent(itemView.context, TestimonyCommentActivity::class.java)
                    intent.putExtra(TestimonyCommentActivity.POST_ID, model.uid)
                    itemView.context.startActivity(intent)
                }

                mark.setOnClickListener {
                    Toast.makeText(itemView.context, "Berhasil menandai postingan ini!", Toast.LENGTH_SHORT).show()
                }

                share.setOnClickListener {
                    Toast.makeText(itemView.context, "Berhasil membagikan postingan ini!", Toast.LENGTH_SHORT).show()
                }

                delete.setOnClickListener {
                    AlertDialog.Builder(itemView.context)
                        .setTitle("Konfirmasi Menghapus Postingan")
                        .setMessage("Apakah anda yakin ingin menghapus postingan ini ?")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setPositiveButton("YA") { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            FirebaseFirestore
                                .getInstance()
                                .collection("testimony")
                                .document(model.uid!!)
                                .delete()
                                .addOnCompleteListener {
                                    if(it.isSuccessful) {
                                        Toast.makeText(itemView.context, "Berhasil menghapus postingan!", Toast.LENGTH_SHORT)
                                            .show()
                                        testimonyList.removeAt(adapterPosition)
                                        notifyDataSetChanged()
                                    }
                                }
                        }
                        .setNegativeButton("TIDAK", null)
                        .show()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTestimonyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(testimonyList[position], testimonyList)
    }

    override fun getItemCount(): Int = testimonyList.size
}