package com.example.gamemobile2d.weapon

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.example.gamemobile2d.entities.Entity

abstract class Weapon {
    var damage: Int = 0
    var angle: Float = 0f
    lateinit var bitmap: Bitmap
    lateinit var spritesAttack: List<Bitmap>

    abstract fun draw(canvas: Canvas)
    abstract fun attack()
}