package com.project.edukasidiabetesmilitus.testimony

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.edukasidiabetesmilitus.databinding.ItemTestimonyBinding

class TestimonyAdapter : RecyclerView.Adapter<TestimonyAdapter.ViewHolder>() {

    private val bookList = ArrayList<TestimonyModel>()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<TestimonyModel>) {
        bookList.clear()
        bookList.addAll(items)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: ItemTestimonyBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: TestimonyModel) {
            with(binding) {

                name.text = model.name
                status.text = model.status
                title.text = model.title
                description.text = model.description

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

                }

                share.setOnClickListener {

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTestimonyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int = bookList.size
}