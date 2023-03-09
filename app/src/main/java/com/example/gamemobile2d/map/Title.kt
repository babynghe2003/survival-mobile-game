package com.example.gamemobile2d.map

import android.graphics.Bitmap
import android.graphics.Rect

class Title() {
    // 0 -> space
    // 1 -> floor
    // 2 -> wall
    // 3 -> tree
    // 4 -> trap

    val title_size: Int = 50
    var x: Float = 0f
    var y: Float = 0f
    lateinit var bitmap: Bitmap
    var isPhasing: Boolean = true

    fun getRect(): Rect{
        return Rect(x.toInt(), y.toInt(), (x + title_size).toInt(), (y + title_size).toInt())
    }

}