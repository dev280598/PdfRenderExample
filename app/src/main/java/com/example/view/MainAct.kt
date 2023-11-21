package com.example.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebasestorage.R
import com.example.firebasestorage.databinding.ActivityMainBinding

class MainAct : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val pickPdfFileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val pdfUri = data?.data
                pdfUri?.let { uri ->
                    Log.e(TAG, "$uri")
                    startActivity(PdfViewerAct.getIntent(this, uri))
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@MainAct, R.layout.activity_main)

        binding.openPdfBtn.setOnClickListener {
            pickPdfFile()
        }
    }


    private fun pickPdfFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = INTENT_TYPE
        }
        pickPdfFileLauncher.launch(intent)
    }


    companion object {
        const val INTENT_TYPE = "application/pdf"
    }
}
const val TAG = "MainAct"