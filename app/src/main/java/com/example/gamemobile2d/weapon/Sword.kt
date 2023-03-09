package com.example.gamemobile2d.weapon

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import com.example.gamemobile2d.R

class Sword(context: Context): Weapon(context) {
    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bloodkatana)
        spritesAttack = listOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.cut_1),
            BitmapFactory.decodeResource(context.resources, R.drawable.cut_2),
            BitmapFactory.decodeResource(context.resources, R.drawable.cut_3),
            BitmapFactory.decodeResource(context.resources, R.drawable.cut_4),
        )
    }

    override fun animate() {
        current_sprites = (++current_sprites) % spritesAttack.size
    }

    override fun draw(canvas: Canvas) {
    }

    fun rotateBitmap(d: Bitmap): Bitmap{
        val m = Matrix()
        m.setRotate(angle)
        val src = d
        val dst = Bitmap.createBitmap(src, 0, 0, src.width, src.height)
        return dst
    }

    override fun attack() {
    }
}