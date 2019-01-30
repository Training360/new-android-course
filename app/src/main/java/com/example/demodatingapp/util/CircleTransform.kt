package com.example.demodatingapp.util

import android.graphics.*
import com.squareup.picasso.Transformation

class CircleTransform: Transformation {

    override fun key(): String {
        return "circle"
    }

    override fun transform(source: Bitmap?): Bitmap {
        val size = Math.min(source!!.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squared = Bitmap.createBitmap(source, x, y, size, size)
        if (squared != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        squared.recycle()
        return bitmap
    }

}