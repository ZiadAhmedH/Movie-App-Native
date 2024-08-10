package com.ziadahmed.logintask

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.net.Uri
import java.io.InputStream

object ImageUtil {

    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val size = Math.min(bitmap.width, bitmap.height)
        val x = (bitmap.width - size) / 2
        val y = (bitmap.height - size) / 2
        val squaredBitmap = Bitmap.createBitmap(bitmap, x, y, size, size)
        val circularBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

        val paint = Paint()
        val canvas = Canvas(circularBitmap)
        val shader = android.graphics.BitmapShader(squaredBitmap, android.graphics.Shader.TileMode.CLAMP, android.graphics.Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, size.toFloat(), size.toFloat())
        canvas.drawOval(rectF, paint)

        return circularBitmap
    }
}