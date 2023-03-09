package com.example.gamemobile2d.weapon

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.example.gamemobile2d.entities.Entity

abstract class Weapon(context: Context) {
    var damage: Int = 0
    var angle: Float = 0f
    lateinit var bitmap: Bitmap
    lateinit var spritesAttack: List<Bitmap>
    var tile_x: Float = 32f
    var tile_y: Float = 32f
    var current_sprites: Int = 0

    abstract fun animate()
    abstract fun draw(canvas: Canvas)
    abstract fun attack()
}