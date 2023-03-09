package com.example.gamemobile2d.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.FrameLayout
import com.example.gamemobile2d.entities.Enemy
import com.example.gamemobile2d.entities.Entity
import com.example.gamemobile2d.entities.Player
import com.example.gamemobile2d.map.Map

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback{
    private var running = false
    private var gameThread: Thread? = null

    private var entities: ArrayList<Entity> = arrayListOf()
    private lateinit var player: Player
    private lateinit var player2: Player
    private lateinit var joystick: Joystick
    private lateinit var joystick2: Joystick
    private lateinit var map: com.example.gamemobile2d.map.Map
    private lateinit var settingPanel: SettingPanel

    private val FPS_RATE = 60
    private var lastTime: Long = 0
    private var lastupdate = 0
    private var cameraX = 0f
    private var cameraY = 0f
    private val cameraScale = 1.5f
    private val playerScale = 1.2f
    private var targetCameraX = 0f
    private var targetCameraY = 0f
    val INTERPOLATION_FACTOR = 0.05f
    var isPause = false

    init{
        holder.addCallback(this)

    }

    private var touched = false
    override fun surfaceCreated(p0: SurfaceHolder) {
        map = Map(this.context)
        joystick = Joystick()
        joystick2 = Joystick()
        settingPanel = SettingPanel(this)
        player = Player(context, joystick, entities, map.map)
        player2= Player(context, joystick2, entities, map.map)
        player2.x = 400f
        player2.y = 400f
        entities.add(player)
//        entities.add(player2)
        for (i in 1..10){
            entities.add(Enemy(context, entities, map.map))
        }
        lastTime = System.nanoTime()
        start()

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()
    }

    private fun start() {
        running = true
        gameThread = Thread {
            var frames = 0
            var fpsTime = System.currentTimeMillis()
            while (running) {
                val startTime = System.nanoTime()
                val elapsedTime = (startTime - lastTime) / 1000000.0
                if (elapsedTime > 10) {
                    lastTime = startTime
                    if (!isPause)
                    update()
                    draw()
                    frames++
                }
                // Calculate and log the FPS every second
                val currentTime = System.currentTimeMillis()
                if (currentTime - fpsTime >= 1000) {
                    val fps = frames.toDouble() / ((currentTime - fpsTime) / 1000.0)
                    Log.d("FPS", "Frames per second: $fps")
                    fpsTime = currentTime
                    frames = 0
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
        if (lastupdate % 4 == 0){
            entities.forEach{it.animate()}
            lastupdate = 0
        }
    }

    fun draw() {
        val canvas = holder.lockCanvas()
        canvas?.let{
            targetCameraX = - player.x + (width/2)/cameraScale
            targetCameraY = - player.y + (height/2)/cameraScale
            cameraX += (targetCameraX - cameraX) * INTERPOLATION_FACTOR
            cameraY += (targetCameraY - cameraY) * INTERPOLATION_FACTOR

            it.translate(x, y)
            it.scale(cameraScale, cameraScale)
            it.translate(-x, -y)
            canvas.translate(cameraX, cameraY)
            it.drawColor(Color.argb(255, 2, 21, 38))
            map.draw(it)

            entities.sortBy { it.y }
            entities.forEach{ it.draw(canvas) }


            map.drawlayer2(it)
            if (touched){
                joystick.render(it, cameraScale,cameraX, cameraY)
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

    fun pauseGame(){
        isPause = true
    }

    fun resumeGame(){
        isPause = false
    }

}