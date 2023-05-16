package com.example.gamemobile2d.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.FrameLayout
import com.example.gamemobile2d.entities.Enemy
import com.example.gamemobile2d.entities.Entity
import com.example.gamemobile2d.entities.Player
import com.example.gamemobile2d.map.Map
import kotlin.math.sqrt

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback{
    var running = false
    private var gameThread: Thread? = null
    var pauseButton: Button? = null
    private var entities: ArrayList<Entity> = arrayListOf()
    private lateinit var player: Player
    private lateinit var player2: Player
    private lateinit var joystick: Joystick
    private lateinit var joystick2: Joystick
    private lateinit var map: com.example.gamemobile2d.map.Map
    private lateinit var settingPanel: SettingPanel

    private var gameStartTime = System.nanoTime()
    private var gameLastTime = 0L
    private var gameTime = 0L
    private var gameLevel = 0
    private val FPS_RATE = 60
    private var lastTime: Long = 0
    private var lastupdate = 0
    private var cameraX = 0f
    private var cameraY = 0f
    private val cameraScale = 1.5f
    private val playerScale = 1.2f
    private var targetCameraX = 0f
    private var targetCameraY = 0f
    val INTERPOLATION_FACTOR = 0.1f
    var isPause = false
    val timePaint = Paint().apply {
        color = Color.BLACK
        textSize = 50f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        style = Paint.Style.FILL
        strokeWidth = 3f
    }
    val timePaintBorder = Paint().apply {
        color = Color.WHITE
        textSize = 50f
        strokeWidth = 7f
        style = Paint.Style.STROKE
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val scorePaint = Paint().apply {
        color = Color.GREEN
        textSize = 45f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        style = Paint.Style.FILL
        strokeWidth = 3f
    }
    val scorePaintBorder = Paint().apply {
        color = Color.WHITE
        textSize = 45f
        strokeWidth = 7f
        style = Paint.Style.STROKE
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val endBackGround = Paint().apply {
        color = Color.parseColor("#80000000")
    }
    val endPaint = Paint().apply {
        color = Color.WHITE
        textSize = 100f
        strokeWidth = 7f
        style = Paint.Style.FILL_AND_STROKE
    }
    val endScorePaint = Paint().apply {
        color = Color.WHITE
        textSize = 60f
        strokeWidth = 4f
        style = Paint.Style.FILL_AND_STROKE
    }

    init{
        holder.addCallback(this)

    }

    private var touched = false
    override fun surfaceCreated(p0: SurfaceHolder) {
//        startGame()

    }

    fun startGame(){
        running = true
        gameThread = null

        entities = arrayListOf()

        gameStartTime = System.nanoTime()
        gameLastTime = 0L
        gameTime = 0L
        gameLevel = 0
        lastTime = 0
        lastupdate = 0
        cameraX = 0f
        cameraY = 0f
        targetCameraX = 0f
        targetCameraY = 0f
        isPause = false
        map = Map(this.context)
        joystick = Joystick()
        joystick2 = Joystick()
        settingPanel = SettingPanel(this)
        player = Player(context, joystick, entities, map.wallMap)
        player.x = 700f
        player.y = 700f
        entities.add(player)
//        entities.add(player2)
        for(i in 0..20+gameLevel*5){
            var ex = (100..1300).random().toFloat()
            var ey = (100..1300).random().toFloat()
            while (sqrt((ex - player.x)*(ex - player.x)
                        + (ey - player.y)*(ey - player.y)).toDouble()<400){
                ex = (100..1300).random().toFloat()
                ey = (100..1300).random().toFloat()
            }
            val enemy = Enemy(context, entities, map.wallMap)
            enemy.x = ex
            enemy.y = ey
            entities.add(enemy)
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
//        running = true
        gameThread = Thread {
            var frames = 0
            var fpsTime = System.currentTimeMillis()
            while (running) {
                val startTime = System.nanoTime()
                val elapsedTime = (startTime - lastTime) / 1000000.0
                if (elapsedTime > 10) {
                    lastTime = startTime
                    if (!isPause){
                        update()
                    draw()
                    }
                    frames++
                }
                // Calculate and log the FPS every second
                val currentTime = System.currentTimeMillis()
                if (currentTime - fpsTime >= 1000) {
                    val fps = frames.toDouble() / ((currentTime - fpsTime) / 1000.0)
                    Log.d("FPS", "Frames per second: $fps and time: $gameTime")

                    fpsTime = currentTime
                    if (!isPause) gameTime++
                    frames = 0
                }
            }
        }
        gameThread?.start()
    }


    private fun stop() {
        running = false
        draw()
        gameThread?.join()
    }

    fun update(){
        if (player.health == 0){
            stop()
        }
        gameTime
        lastupdate++
        entities.forEach{

            if (it.isAlive && it.x > player.x - width/2 && it.x < player.x + width/2 &&  it.y > player.y - height/2 && it.y < player.y + height/2) {
                it.update()
                if (it.health == 0 && it is Enemy){
                    it.isAlive = false
                }
            }
        }
        if (lastupdate % 3 == 0){
            entities.forEach {
                if (it.isAlive) {
                    it.animate()
                }
            }
            lastupdate = 0
        }
        entities.removeIf{
            it.isAlive == false
        }
        if (gameTime%15L==0L && gameLastTime<gameTime){
            for(i in 0..20+gameLevel*10){
                var ex = (100..1300).random().toFloat()
                var ey = (100..1300).random().toFloat()
                while (sqrt((ex - player.x)*(ex - player.x)
                            + (ey - player.y)*(ey - player.y)).toDouble()<400){
                    ex = (100..1300).random().toFloat()
                    ey = (100..1300).random().toFloat()
                }
                val enemy = Enemy(context, entities, map.wallMap)
                enemy.x = ex
                enemy.y = ey
                entities.add(enemy)
            }
            entities.forEach{
                it.isLevelUp(gameLevel)
            }
            gameLevel++
        }
        gameLastTime = gameTime

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
            entities.forEach{
                if (it.x > player.x - width/2 && it.x < player.x + width/2 &&  it.y > player.y - height/2 && it.y < player.y + height/2)
                    it.draw(canvas)
            }


            map.drawlayer2(it)
            if (touched && !isPause){
                joystick.render(it, cameraScale,cameraX, cameraY)
            }
            it.drawText("SCORE: ${player.score}", 50f -  cameraX, 100f - cameraY, scorePaintBorder)
            it.drawText("SCORE: ${player.score}", 50f -  cameraX, 100f - cameraY, scorePaint)
            it.drawText("Time: ${gameTime.toString()}", width/2/cameraScale + 50 -  cameraX, 100f - cameraY, timePaintBorder)
            it.drawText("Time: ${gameTime.toString()}", width/2/cameraScale + 50 - cameraX, 100f - cameraY, timePaint)
            if (!running){
                it.drawRect( -2000f, -2000f, 100000f, 100000f, endBackGround)
                it.drawText("GAME OVER", -cameraX + width/2/cameraScale - endPaint.measureText("GAME OVER")/2, height/2/cameraScale - cameraY, endPaint)
                it.drawText("Your score: ${player.score}",  -cameraX + width/2/cameraScale - endScorePaint.measureText("Your score: ${player.score}")/2, height/2/cameraScale + 200f - cameraY, endScorePaint)
            }
            if (isPause){
                it.drawRect( -2000f, -2000f, 100000f, 100000f, endBackGround)
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