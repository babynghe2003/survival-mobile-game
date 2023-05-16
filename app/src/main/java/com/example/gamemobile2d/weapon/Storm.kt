package com.example.gamemobile2d.weapon

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.example.gamemobile2d.R
import com.example.gamemobile2d.entities.Player
import kotlin.math.cos
import kotlin.math.sin

class Storm(player: Player, context: Context): Weapon(context) {
    var tileSize = 200f
    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.storm_1)
        spritesAttack = listOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.storm_1),
            BitmapFactory.decodeResource(context.resources, R.drawable.storm_2),
            BitmapFactory.decodeResource(context.resources, R.drawable.storm_3),
            BitmapFactory.decodeResource(context.resources, R.drawable.storm_4),
            BitmapFactory.decodeResource(context.resources, R.drawable.storm_5),
            BitmapFactory.decodeResource(context.resources, R.drawable.storm_6),
        )
        playerOwner = player
    }
    override fun animate() {
        current_sprites = (++current_sprites) % spritesAttack.size
        angle = (angle + Math.PI / 100).toFloat()
        if (angle > 2*Math.PI) angle = 0f
        tile_x = cos(angle)*300 + playerOwner.x
        tile_y = sin(angle)*300 + playerOwner.y
    }

    override fun draw(canvas: Canvas) {
        var dst = Rect(tile_x.toInt(), tile_y.toInt(), (tile_x+tileSize + level * 10).toInt(), (tile_y+tileSize + level * 10).toInt())
        canvas.drawBitmap(spritesAttack[current_sprites], null, dst, null)
    }

    override fun attack() {
        TODO("Not yet implemented")
    }

    override fun getHitbox(): Rect {
        return Rect(
            (tile_x).toInt(),
            (tile_y).toInt(),
            (tile_x + tileSize + level * 10).toInt(),
            (tile_y + tileSize + level * 10).toInt()
        )
    }
}