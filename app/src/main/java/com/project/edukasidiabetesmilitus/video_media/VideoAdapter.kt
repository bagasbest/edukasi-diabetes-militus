package com.project.edukasidiabetesmilitus.video_media

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.edukasidiabetesmilitus.databinding.ItemVideoBinding

class VideoAdapter(private val videoList : ArrayList<VideoModel>) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: VideoModel) {
            with(binding) {

                name.text = model.name
                duration.text = model.duration
                Glide.with(itemView.context)
                    .load(model.image)
                    .into(image)

                streamingBtn.setOnClickListener {
                    val intent = Intent(itemView.context, VideoDetailActivity::class.java)
                    intent.putExtra(VideoDetailActivity.VIDEO, model.link)
                    itemView.context.startActivity(intent)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoList[position])
    }

    override fun getItemCount(): Int = videoList.size
}