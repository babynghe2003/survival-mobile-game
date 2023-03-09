package com.example.gamemobile2d

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.SurfaceView
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.gamemobile2d.ui.GameView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        setContentView(R.layout.activity_main)
        val gameContainer = findViewById<FrameLayout>(R.id.gamecontainer)
        val gameview = findViewById<GameView>(R.id.gameview)

        val pauseButton = Button(this)
        pauseButton.text = "Pause"
        pauseButton.setOnClickListener {
            if (gameview.isPause) {
                gameview.resumeGame()
                pauseButton.text = "Pause" // update button text
            } else {
                gameview.pauseGame()
                pauseButton.text = "Resume" // update button text
            }
        }

        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.TOP or Gravity.END
        )

        gameContainer.addView(pauseButton, layoutParams)
        hideSystemUI()
    }

    private fun hideSystemUI(){
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window,
            window.decorView.findViewById(android.R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior  = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        }
    }
}