package com.elliotgrin.ticketer.util

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import androidx.core.content.ContextCompat
import com.elliotgrin.ticketer.R
import com.elliotgrin.ticketer.model.CityMarker
import com.elliotgrin.ticketer.util.ext.dpToPx
import com.elliotgrin.ticketer.util.ext.spToPx
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val BITMAP_WIDTH_DP = 55f
private const val BITMAP_HEIGHT_DP = 30f
private const val CORNER_RADIUS = 50f
private const val TEXT_SIZE_SP = 16f
private const val STROKE_WIDTH_PX = 4F

class MapMarkerUtil(private val context: Context) {

    private val width = BITMAP_WIDTH_DP.dpToPx()
    private val height = BITMAP_HEIGHT_DP.dpToPx()
    private val textSizePx = TEXT_SIZE_SP.spToPx()

    fun createCityMarker(marker: CityMarker): MarkerOptions {
        val bitmap = createBitmapFromMarker(marker)
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
        return MarkerOptions()
            .position(marker.location)
            .icon(bitmapDescriptor)
            .anchor(0.5f, 0.5f)
            .alpha(0.8f)
    }

    fun createPlaneMarker(position: LatLng): MarkerOptions {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_plane)
        val scaleBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(scaleBitmap)
        return MarkerOptions()
            .position(position)
            .flat(true)
            .anchor(0.5f, 0.5f)
            .icon(bitmapDescriptor)
    }

    private fun createBitmapFromMarker(marker: CityMarker): Bitmap {
        val bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        drawRoundedRectangleWithStroke(canvas)
        drawText(canvas, marker.title)

        return bitmap
    }

    private fun drawRoundedRectangleWithStroke(canvas: Canvas) {
        val rectanglePaint = getRectanglePaint()
        val borderPaint = getBorderPoint()
        val rect = getRect()
        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, rectanglePaint)
        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, borderPaint)
    }

    private fun drawText(canvas: Canvas, text: String) {
        val paint = getTextPaint()
        // Center the text position
        val x = width / 2
        val y = (height / 2) - (paint.descent() + paint.ascent()) / 2
        canvas.drawText(text, x, y, paint)
    }

    private fun getRectanglePaint() = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.orange)
    }

    private fun getBorderPoint() = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = STROKE_WIDTH_PX
    }

    private fun getTextPaint() = TextPaint().apply {
        color = Color.WHITE
        textSize = textSizePx
        textAlign = Paint.Align.CENTER
    }

    private fun getRect() = RectF(
        STROKE_WIDTH_PX,
        STROKE_WIDTH_PX,
        width - STROKE_WIDTH_PX,
        height - STROKE_WIDTH_PX
    )

}
