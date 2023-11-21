package com.example.view

import android.graphics.pdf.PdfRenderer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.extension.renderAndClose
import com.example.firebasestorage.databinding.PdfPageItemBinding

class PdfAdapter(
    private val renderer: PdfRenderer,
) : RecyclerView.Adapter<PdfViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PdfViewHolder.create(
            PdfPageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        holder.bind(renderer.openPage(position).renderAndClose())
    }

    override fun getItemCount() : Int {
        Log.e(TAG,"${renderer.pageCount}")
        return renderer.pageCount
    }

}
