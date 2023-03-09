package com.example.gamemobile2d.utils

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Shadow() {
    private val paint: Paint
    init {
        paint = Paint().apply {
            isAntiAlias = true
            color = Color.argb(255, Color.red(Color.BLACK), Color.green(Color.BLACK), Color.blue(
                Color.BLACK))
            isDither = true
            style = Paint.Style.FILL
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 20f
            maskFilter = BlurMaskFilter(7f, BlurMaskFilter.Blur.NORMAL)
            alpha = 170
        }
    }
    fun draw(canvas: Canvas,  entityX: Float,  entityY: Float,  entityWidth: Float,  entityHeight: Float){

        canvas.drawOval(entityX+5, entityY + entityHeight-10, entityX+entityWidth-5, entityY + entityHeight + 5, paint )
    }
}