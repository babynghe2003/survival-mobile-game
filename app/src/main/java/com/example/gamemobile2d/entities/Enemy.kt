package com.example.gamemobile2d.entities

import android.content.Context
import android.graphics.*
import androidx.core.graphics.minus
import com.example.gamemobile2d.R
import com.example.gamemobile2d.map.Title
import com.example.gamemobile2d.utils.Shadow
import kotlin.math.atan2
import kotlin.math.*
import kotlin.random.Random

class Enemy(context: Context, entities: List<Entity>, map: ArrayList<Title>): Entity(context, entities, map) {

    var isFlip = false
    var isAttacking = false
    var isTracking = false
    private var currentSpriteIndex = 0
    val random = Random

    init{
        x = (100..1300).random().toFloat()
        y = (100..1300).random().toFloat()
        speed = 6f
        shadow = Shadow()
        visionRange = 600f
        spritesStand = listOf()
        spritesMove = listOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.babydragon_1),
            BitmapFactory.decodeResource(context.resources, R.drawable.babydragon_2),
            BitmapFactory.decodeResource(context.resources, R.drawable.babydragon_3),
            BitmapFactory.decodeResource(context.resources, R.drawable.babydragon_4),
        )
        tileWidth = spritesMove[0].width/2.toFloat()
        tileHeight = spritesMove[0].height/2.toFloat()
        attackRange = max(tileHeight, tileWidth) + 5f

    }
    fun checkVision(){
        var minDistance = 1000000f
        isTracking = false
        for (ett in entities){
            if (ett is Player){
                val cX = getHitbox().centerX().toFloat()
                val cY = getHitbox().centerY().toFloat()
                val distance = sqrt(((ett.getHitbox().centerX() - cX)*(ett.getHitbox().centerX() - cX)
                        + (ett.getHitbox().centerY() - cY)*(ett.getHitbox().centerY() - cY)).toDouble())
                if (distance < attackRange){
                    ett.isDamaged(10,10, x, y)
                    this.isDamaged(0, 20, ett.x, ett.y)
                }
                if (distance < visionRange && distance <= minDistance){
                    minDistance = distance.toFloat()
                    isTracking = true
                    angle = atan2(ett.getHitbox().centerY() - cY, ett.getHitbox().centerX() - cX)
                }
            }
        }
    }
    override fun update() {
        checkVision()
        if (isTracking){
            checkForXCollision(cos(angle) * speed)
            checkForYCollision(sin(angle)*speed)
            isFlip = cos(angle) < 0
        }
    }

    override fun draw(canvas: Canvas) {
        var bitmap = spritesMove[currentSpriteIndex]
        val dst = Rect(x.toInt(), y.toInt(), (x+tileWidth).toInt(), (y+tileHeight).toInt())
        if (isFlip) {
            canvas.drawBitmap(flip(bitmap), null, dst , null)
        } else {
            canvas.drawBitmap(bitmap, null, dst, null)
        }
        shadow.draw(canvas, x, y, tileWidth, tileHeight)
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
        currentSpriteIndex = (currentSpriteIndex + 1) % spritesMove.size
    }

    override fun isDamaged(dame: Int, push: Int, dameX: Float, dameY: Float) {
        val dameAngle = atan2(dameY - y, dameX - x)
        checkForXCollision(-cos(dameAngle) *push)
        checkForYCollision(-sin(dameAngle) *push)
        if (health >= 0) {
            health -= dame
        }
        else {
            health = 0
        }
    }

}