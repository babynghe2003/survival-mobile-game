package com.example.gamemobile2d.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint


class SettingPanel(gameView: GameView) {
    private lateinit var options: List<Option>
    private val paint = Paint().apply{
        isAntiAlias = true
        color = Color.WHITE
        isDither = true
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2f
    }
    init{
        options = listOf(
            Option("Menu 1", 150f, 100f, 1000f, 200f),
            Option("Menu 2", 150f, 250f, 1000f, 350f),
            Option("Menu 3", 150f, 400f, 1000f, 500f),
        )
    }

    fun render(canvas: Canvas){
        canvas.drawRect(50f, 50f, 1100f, 550f, paint)
        for (option in options){
            option.render(canvas)
        }
    }

}
class Option(val text: String,val x: Float,val y: Float,val width: Float,val height: Float){
    private var paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        isDither = true
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }
    fun render(canvas: Canvas){
        canvas.drawRect(x, y, width, height, paint)
        canvas.drawText(text, x+10, y+10, paint)
    }
    fun onTouch(){

    }
}