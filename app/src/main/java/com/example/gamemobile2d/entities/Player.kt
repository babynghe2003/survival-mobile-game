package com.example.gamemobile2d.entities

import android.content.Context
import android.graphics.*
import android.util.Half.toFloat
import com.example.gamemobile2d.R
import com.example.gamemobile2d.map.Title
import com.example.gamemobile2d.ui.Joystick
import com.example.gamemobile2d.utils.Shadow
import com.example.gamemobile2d.utils.StatusBar
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


class Player(context: Context, private val joystick: Joystick, entities: List<Entity>, map: ArrayList<Title>) :
    Entity(context, entities, map) {
    var isFlip = false
    val statusBar: StatusBar
    private val maxHealth = 100

    init {
        shadow = Shadow()
        statusBar = StatusBar()
        x = 100f
        y = 100f
        angle = 0f
        speed = 16f
        health = 100


        spritesStand = listOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_1),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_2),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_3),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_4),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_5)
        )

        tileWidth = (spritesStand[1].width*4/3).toFloat()
        tileHeight = (spritesStand[1].height*4/3).toFloat()

        spritesMove = listOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_move_1),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_move_2),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_move_3),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_move_4),
            BitmapFactory.decodeResource(context.resources, R.drawable.assasin_move_5)
        )
    }

    private var currentSpriteIndex = 0


    override fun update() {
        angle = joystick.getAngle()
        if (joystick.isMoving()) {
            checkForXCollision(cos(angle) * speed * joystick.getSpeed())
            checkForYCollision(sin(angle) * speed * joystick.getSpeed())

            isFlip = cos(angle) * speed < 0
        }
    }


    override fun draw(canvas: Canvas) {
        var bitmap: Bitmap? = null
        var dst = Rect(x.toInt(),y.toInt(), (x+tileWidth).toInt(), (y+tileHeight).toInt())
        if (joystick.isMoving()) {
            bitmap = spritesMove[currentSpriteIndex]

        } else {
            bitmap = spritesStand[currentSpriteIndex]

        }
        if (isFlip) {
            canvas.drawBitmap(flip(bitmap), null, dst, null)
        } else {
            canvas.drawBitmap(bitmap, null, dst, null)
        }

        shadow.draw(canvas, x, y, tileWidth, tileHeight)
        statusBar.draw(canvas, x, y, tileWidth, tileHeight, maxHealth, health)
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

    override fun isDamaged(dame: Int, push: Int, dameX: Float, dameY: Float) {
        val dameAngle = atan2(dameY - y, dameX - x)
        checkForXCollision(-cos(dameAngle) *push)
        checkForYCollision(-sin(dameAngle) * push)
        if (health >= 0) {
            health -= dame
        }
        if (health <= 0) {
            health = 0
        }
    }
}