package com.example.gamemobile2d.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import androidx.core.graphics.minus
import com.example.gamemobile2d.map.Title
import com.example.gamemobile2d.utils.Shadow
import kotlin.math.abs
import kotlin.math.atan2

abstract class Entity(context: Context, val entities: List<Entity>, val map: ArrayList<Title>) {
    var tileWidth = 0f
    var tileHeight = 0f
    lateinit var shadow: Shadow

    var x = 0f;
    var y = 0f;

    var angle = 0f
    var speed = 2f
    var visionRange = 300f
    var attackRange = 100f
    var health = 100
    var isAlive = true

    lateinit var spritesStand: List<Bitmap>
    lateinit var spritesMove: List<Bitmap>

    enum class CollisionDirection {
        NONE,
        LEFT,
        RIGHT,
        DOWN,
        UP
    }

    fun getHitbox(): Rect {
        return Rect(
            (x + 10).toInt(),
            (y + tileHeight / 2).toInt(),
            (x + tileWidth - 20).toInt(),
            (y + tileHeight).toInt()
        )
    }

    fun checkForXCollision(dx: Float) {
        var oldX = x
        x += dx
        for (ett in entities) {
            if (ett != this) {
                val colli = ett.getHitbox()
                if (!Rect.intersects(colli, getHitbox())) {
                    continue
                }

                val dex = ett.getHitbox().centerX() - getHitbox().centerX()

                if (dex > 0) {
                    x -= (getHitbox().right - ett.getHitbox().left + 1)
                    oldX = x
                } else if (dex < 0) {
                    x += (ett.getHitbox().right - getHitbox().left + 1)
                    oldX = x
                }
            }
        }

        for (tile in map) {

                val colli = tile.getRect()
                if (!Rect.intersects(colli, getHitbox())) {
                    continue
                }

                val dex = tile.getRect().centerX() - getHitbox().centerX()

                if (dex > 0) {
                    x -= (getHitbox().right - tile.getRect().left + 1)
                    oldX = x
                } else if (dex < 0) {
                    x += (tile.getRect().right - getHitbox().left + 2)
                    oldX = x
                }


        }

    }

    fun checkForYCollision(dy: Float) {
        var oldY = y
        y += dy
        for (ett in entities) {
            if (ett != this) {
                val colli = ett.getHitbox()
                if (!Rect.intersects(colli, getHitbox())) {
                    continue
                }

                val dey = ett.getHitbox().centerY() - getHitbox().centerY()

                if (dey > 0) {
                    y = oldY
                    oldY = y
                } else if (dy < 0) {
                    y = oldY
                    oldY = y
                }
            }
        }

        for (tile in map) {
                val colli = tile.getRect()
                if (!Rect.intersects(colli, getHitbox())) {
                    continue
                }

                val dey = tile.getRect().centerX() - getHitbox().centerY()

                if (dey > 0) {
                    y = oldY
                    oldY = y
                } else if (dey < 0) {
                    y = oldY
                    oldY = y
                }
        }

    }

    abstract fun update()
    abstract fun draw(canvas: Canvas)
    abstract fun animate()
    abstract fun isDamaged(dame: Int, push: Int, dameX: Float, dameY: Float)
    abstract fun isLevelUp(gameLevel: Int)


}