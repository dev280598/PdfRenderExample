package com.example.view

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.firebasestorage.R
import com.example.firebasestorage.databinding.PdfViewerActBinding


class PdfViewerAct : AppCompatActivity() {

    private lateinit var binding: PdfViewerActBinding
    private lateinit var pdfRenderer: PdfRenderer
    private lateinit var descriptor: ParcelFileDescriptor

    private var pdfAdapter: PdfAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@PdfViewerAct, R.layout.pdf_viewer_act)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.isDraw = false
        displayPage()
        onActionDraw()
    }

    private fun initRec() {
        pdfAdapter = PdfAdapter(pdfRenderer)
        binding.recPdf.layoutManager = LinearLayoutManager(this)
        binding.recPdf.setHasFixedSize(true)
        binding.recPdf.adapter = pdfAdapter
    }

    private fun onActionDraw() {
        binding.drawFab.setOnClickListener {
            binding.isDraw = binding.isDraw != true

            if (binding.isDraw == false) {
                Toast.makeText(this, getString(R.string.disable_draw), Toast.LENGTH_LONG).show()
                binding.drawView.onResetData()
            } else {
                Toast.makeText(this, getString(R.string.enable_draw), Toast.LENGTH_LONG).show()
            }

            val mOnItemTouchListener: OnItemTouchListener = object : OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return binding.isDraw ?: false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            }
            binding.recPdf.addOnItemTouchListener(mOnItemTouchListener)
        }
    }

    private fun displayPage() {
        val pdfAsset = assets.openFd(PDF_FROM_ASSETS).parcelFileDescriptor
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(URI_PDF, Uri::class.java)
        } else {
            intent.getParcelableExtra(URI_PDF)
        }
        uri?.let {
            descriptor = contentResolver.openFileDescriptor(it, "r") ?: pdfAsset
            pdfRenderer = PdfRenderer(descriptor)
            val page: PdfRenderer.Page = pdfRenderer.openPage(0)
            initRec()
            page.close()
        }
    }

    override fun onDestroy() {
        pdfRenderer.close()
        descriptor.close()
        super.onDestroy()
    }

    companion object {
        const val PDF_FROM_ASSETS = "pdf/cover_letter.pdf"
        const val URI_PDF = "URI_PDF_PICKED"

        fun getIntent(context: Context, uri: Uri): Intent {
            val intent = Intent(context, PdfViewerAct::class.java).apply {
                putExtra(URI_PDF, uri)
            }
            return intent
        }
    }
}