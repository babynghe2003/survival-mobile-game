package com.example.gamemobile2d.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import com.example.gamemobile2d.entities.Player
import kotlin.math.*

class Joystick {
    private val joystickRadius = 150f
    private val handleRadius = 100f
    private val paint: Paint

    private var x = 0f
    private var y = 0f
    private var angle = 0f
    private var moving = false

    init {
        paint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            isDither = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 20f
        }
    }

    fun getAngle(): Float{
        return angle
    }

    fun onTouchDown(motionEvent: MotionEvent){
        moving = true
        x = motionEvent.x
        y = motionEvent.y
    }

    fun onTouchUp(){
        moving = false
    }

    fun onTouchMove(motionEvent: MotionEvent){
        angle = atan2(motionEvent.y - y, motionEvent.x - x)
    }

    fun render(canvas: Canvas, SCALE: Float, player: Player, transX: Float, transY: Float){
        canvas.drawCircle(x/SCALE - transX,y/SCALE - transY, joystickRadius/SCALE, paint)
        canvas.drawCircle((x + cos(angle)*joystickRadius)/SCALE - transX, (y + sin(angle)*joystickRadius)/SCALE - transY, handleRadius/SCALE, paint)
    }

    fun isMoving(): Boolean{
        return moving
    }

}