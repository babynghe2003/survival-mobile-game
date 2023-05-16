package com.example.gamemobile2d.ui

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import android.os.Build.VERSION_CODES.P
import android.view.MotionEvent
import com.example.gamemobile2d.entities.Player
import kotlin.math.*

class Joystick {
    private val joystickRadius = 150f
    private val handleRadius = 100f
    private val paint: Paint


    private var x = 0f
    private var y = 0f
    private var dist = 0f
    private var angle = 0f
    private var moving = false

    init {
        paint = Paint().apply {
            isAntiAlias = true
            color = Color.argb(255, Color.red(Color.BLACK), Color.green(Color.BLACK), Color.blue(Color.BLACK))
            isDither = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 20f
            maskFilter = BlurMaskFilter(12f, BlurMaskFilter.Blur.NORMAL)
        }
    }

    fun getAngle(): Float{
        return angle
    }

    fun getSpeed(): Float{
        return sqrt(dist)/12f
    }

    fun onTouchDown(motionEvent: MotionEvent){
        moving = true
        x = motionEvent.x
        y = motionEvent.y
        dist = 0f
    }

    fun onTouchUp(){
        moving = false
    }

    fun onTouchMove(motionEvent: MotionEvent){
        angle = atan2(motionEvent.y - y, motionEvent.x - x)
        dist = sqrt((motionEvent.x - x)*(motionEvent.x - x) + (motionEvent.y - y)*(motionEvent.y - y))
        if (dist > 150){
            dist = 150f
        }
    }

    fun render(canvas: Canvas, SCALE: Float, transX: Float, transY: Float){
        canvas.drawCircle(x/SCALE - transX,y/SCALE - transY, joystickRadius/SCALE, paint)
        canvas.drawCircle((x + cos(angle)*dist)/SCALE - transX, (y + sin(angle)*dist)/SCALE - transY, handleRadius/SCALE, paint)
    }

    fun isMoving(): Boolean{
        return moving
    }

}