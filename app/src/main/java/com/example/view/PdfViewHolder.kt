package com.example.view

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasestorage.databinding.PdfPageItemBinding

class PdfViewHolder(
    private val binding: PdfPageItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bitmap: Bitmap) {
        binding.imageView.setImageBitmap(bitmap)
    }

    companion object {
        fun create(binding: PdfPageItemBinding) : PdfViewHolder {
            return PdfViewHolder(binding)
        }
    }

}