package com.project.edukasidiabetesmilitus.image_media

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.edukasidiabetesmilitus.databinding.ItemImageBinding

class ImageAdapter(private val imageList : ArrayList<ImageModel>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: ImageModel) {
            with(binding) {

                name.text = model.name
                Glide.with(itemView.context)
                    .load(model.image)
                    .into(image)

                image.setOnClickListener {
                    val intent = Intent(itemView.context, ImageDetailActivity::class.java)
                    intent.putExtra(ImageDetailActivity.IMAGE, model.image)
                    itemView.context.startActivity(intent)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size
}