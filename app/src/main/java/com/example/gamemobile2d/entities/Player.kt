package com.example.gamemobile2d.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import com.example.gamemobile2d.R
import com.example.gamemobile2d.ui.Joystick
import kotlin.math.cos
import kotlin.math.sin


class Player(context: Context, private val joystick: Joystick): Entity(context) {
    var isFlip = false


    init {
        x = 100f
        y = 100f
        angle = 0f
        speed = 16f

        spritesStand = listOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinright_1),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinright_2),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinright_3),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinright_4),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinright_5)
        )

        spritesMove = listOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinmoveright_1),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinmoveright_2),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinmoveright_3),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinmoveright_4),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasinmoveright_5)
        )
    }
    private var currentSpriteIndex = 0


    override fun update() {
        angle = joystick.getAngle()
        if (joystick.isMoving()){
            x += cos(angle)*speed
            y += sin(angle)*speed
            if (cos(angle)*speed < 0){
                isFlip = true
            }else {
                isFlip = false
            }
        }

    }



    override fun draw(canvas: Canvas) {
        var bitmap: Bitmap? = null;
        if (joystick.isMoving()){
            bitmap = spritesMove[currentSpriteIndex]

        }else{
            bitmap = spritesStand[currentSpriteIndex]

        }
        if (isFlip){
            canvas.drawBitmap(flip(bitmap), x, y, null)
        }else{
            canvas.drawBitmap(bitmap, x, y, null)
        }


    }

    fun flip(d: Bitmap): Bitmap {
        val m = Matrix()
        m.preScale(-1f, 1f)
        val src = d
        val dst = Bitmap.createBitmap(src, 0, 0, src.width, src.height, m, false)
//        dst.density = DisplayMetrics.DENSITY_DEFAULT
        return dst
    }

    override fun animate() {
        currentSpriteIndex = (currentSpriteIndex + 1) % spritesStand.size
    }

    override fun isDamaged(dame: Long) {
        x -= cos(angle)*dame
        y -= sin(angle)*dame
    }
}