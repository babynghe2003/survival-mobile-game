package com.example.gamemobile2d.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

abstract class Entity(context: Context) {
    var titleWidth = 100f
    var titleHeight = 100f
    var rect: Rect? = null

    var x = 0f;
    var y = 0f;
    var angle = 0f
    var speed = 2f

    lateinit var spritesStand: List<Bitmap>
    lateinit var spritesMove: List<Bitmap>

    abstract fun update()
    abstract fun draw(canvas: Canvas)
    abstract fun animate()
    abstract fun isDamaged(dame: Long)



}