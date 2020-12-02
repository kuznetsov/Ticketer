package com.elliotgrin.ticketer.util

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.model.Marker
import com.elliotgrin.ticketer.util.ext.dpToPx

private const val BITMAP_WIDTH = 52 // dp
private const val BITMAP_HEIGHT = 30 // dp
private const val CORNER_RADIUS = 50f // dp
private const val TEXT_SIZE = 18 // sp

class MapMarkerUtil(private val context: Context) {

    private val width = BITMAP_WIDTH.dpToPx()
    private val height = BITMAP_HEIGHT.dpToPx()
    private val textSizeInPx = TEXT_SIZE.dpToPx().toFloat()

    fun createBitmapFromMarker(marker: Marker): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw rounded rectangle
        drawRoundedRectangleWithBorder(canvas)

        // Draw text
        drawText(canvas, marker.title)

        return bitmap
    }

    private fun drawRoundedRectangleWithBorder(canvas: Canvas) {
        val rectanglePaint = getRectanglePaint()
        val borderPaint = getBorderPoint()
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rectF, CORNER_RADIUS, CORNER_RADIUS, rectanglePaint)
        canvas.drawRoundRect(rectF, CORNER_RADIUS, CORNER_RADIUS, borderPaint)
    }

    private fun drawText(canvas: Canvas, text: String) {
        val paint = getTextPaint()
        val x = width / 2f
        val y = (height / 2f) - (paint.descent() + paint.ascent()) / 2f
        canvas.drawText(text, x, y, paint)
    }

    private fun getRectanglePaint() = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.orange)
    }

    // TODO: 02.12.2020 Fix border
    private fun getBorderPoint() = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 4f
    }

    private fun getTextPaint() = Paint().apply {
        color = Color.WHITE
        textSize = textSizeInPx
        textAlign = Paint.Align.CENTER
    }

}
