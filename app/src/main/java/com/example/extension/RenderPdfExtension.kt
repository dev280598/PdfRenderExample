package com.example.extension

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import java.io.File
import java.io.InputStream

fun PdfRenderer.Page.renderAndClose() = use {
    val bitmap = createBitmap(this.width)
    render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
    bitmap
}

private fun PdfRenderer.Page.createBitmap(bitmapWidth: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(
        bitmapWidth, (bitmapWidth.toFloat() / width * height).toInt(), Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.WHITE)
    canvas.drawBitmap(bitmap, 0f, 0f, null)

    return bitmap
}

fun InputStream.saveToFile(filePath: String, fileName: String): File = use { input ->
    val file = File(filePath, fileName)
    file.outputStream().use { output ->
        input.copyTo(output)
    }
    input.close()
    file
}