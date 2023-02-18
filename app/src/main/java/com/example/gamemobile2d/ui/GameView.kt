package com.example.gamemobile2d.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.*
import com.example.gamemobile2d.R
import com.example.gamemobile2d.entities.Entity
import com.example.gamemobile2d.entities.Player
import com.example.gamemobile2d.map.Map
import java.util.jar.Attributes

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback{
    private var running = false
    private var gameThread: Thread? = null

    private var entities: ArrayList<Entity> = arrayListOf()
    private lateinit var player: Player
    private lateinit var joystick: Joystick
    private lateinit var map: com.example.gamemobile2d.map.Map

    private val SCALE = 1.5f
    private val FPS_RATE = 60
    private var lastTime: Long = 0
    private var lastupdate = 0

    init{
        holder.addCallback(this)
    }

    private var touched = false
    override fun surfaceCreated(p0: SurfaceHolder) {
        map = Map(this.context)
        joystick = Joystick()
        player = Player(context, joystick)

        entities.add(player)
        lastTime = System.nanoTime()
        start()

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()
    }

    private fun start(){
        running = true
        gameThread = Thread {
            while (running) {
                val startTime = System.nanoTime()
                val elapsedTime = (startTime - lastTime) / 1000000.0
                if (elapsedTime > 10){
                    lastTime = startTime
                    update()
                    draw()
                }
            }
        }
        gameThread?.start()
    }

    private fun stop() {
        running = false
        gameThread?.join()
    }

    fun update(){
        lastupdate++
        entities.forEach{it.update()}
        if (lastupdate % 5 == 0){
            entities.forEach{it.animate()}
            lastupdate = 0
        }

    }

    fun draw() {
        val canvas = holder.lockCanvas()
        canvas?.let{
            val transX = -player.x + (width/2)/SCALE
            val transY = -player.y + (height/2)/SCALE
            it.translate(x, y)
            it.scale(SCALE, SCALE)
            it.translate(-x, -y)
            canvas.translate(transX, transY)
            it.drawColor(Color.LTGRAY)
            map.draw(it)
            entities.forEach{ it.draw(canvas) }
            if (touched){
                joystick.render(it, SCALE, player, transX, transY)
            }
            holder.unlockCanvasAndPost(canvas)
        }


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action){
            MotionEvent.ACTION_DOWN -> {
                touched = true
                joystick.onTouchDown(event)
            }
            MotionEvent.ACTION_MOVE ->{
                touched = true
                joystick.onTouchMove(event)
            }
            MotionEvent.ACTION_UP ->{
                touched = false
                joystick.onTouchUp()
            }
            MotionEvent.ACTION_CANCEL -> {
                touched = false
                joystick.onTouchUp()
            }
            MotionEvent.ACTION_OUTSIDE -> {
                touched = false
                joystick.onTouchUp()
            }
        }
        return true
    }

}