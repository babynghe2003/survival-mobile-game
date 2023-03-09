package com.example.gamemobile2d.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class StatusBar {
    private val healthPaint: Paint
    private val borderPaint: Paint
    private val dividerPaint: Paint
    init {
        healthPaint = Paint().apply {
            isAntiAlias = true
            color = Color.GREEN
            isDither = true
            style = Paint.Style.FILL
            strokeWidth = 0f
        }
        borderPaint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            isDither = true
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 2f
        }
        dividerPaint = Paint().apply {
            isAntiAlias = true
            color = Color.DKGRAY
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }

    fun draw(canvas: Canvas, entityX: Float, entityY: Float, entityWidth: Float, entityHeight: Float, maxHealth: Int, health: Int){
        val sttW = entityWidth
        val sttH = 15f
        val dst = Rect(entityX.toInt() + 5, (entityY - sttH).toInt(), (entityX+sttW).toInt(), (entityY - 5).toInt() )
        val heDst = Rect(entityX.toInt() + 5, (entityY - sttH).toInt(), (entityX + sttW*health/maxHealth).toInt(), (entityY - 5).toInt() )
        canvas.drawRect(dst, borderPaint)
        canvas.drawRect(heDst, healthPaint)
        val numberLine = maxHealth/10
        val widthLine = (sttW - 4 ) / numberLine
        for(i in 0..10){
            canvas.drawLine(entityX + 3 + i*widthLine, entityY - sttH, entityX + 3 + i*widthLine, entityY - 5, dividerPaint)

        }
    }
}