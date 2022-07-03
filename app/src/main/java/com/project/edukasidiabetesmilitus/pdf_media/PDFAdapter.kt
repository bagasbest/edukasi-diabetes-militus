package com.project.edukasidiabetesmilitus.pdf_media

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.edukasidiabetesmilitus.databinding.ItemPdfBinding

class PDFAdapter(private val pdfList : ArrayList<PDFModel>) : RecyclerView.Adapter<PDFAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPdfBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: PDFModel) {
            with(binding) {

                name.text = model.name
                detail.text = model.detail

                readBtn.setOnClickListener {
                    val intent = Intent(itemView.context, PDFDetailActivity::class.java)
                    intent.putExtra(PDFDetailActivity.PDF_LINK, model.link)
                    itemView.context.startActivity(intent)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPdfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pdfList[position])
    }

    override fun getItemCount(): Int = pdfList.size
}