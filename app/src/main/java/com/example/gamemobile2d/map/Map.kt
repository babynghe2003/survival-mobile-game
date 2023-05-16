package com.example.gamemobile2d.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import androidx.core.graphics.get
import com.example.gamemobile2d.R
import java.io.File
import kotlin.random.Random

class Map(private val context: Context) {
    val titles: Array<Title>
    val map: ArrayList<Title> = arrayListOf()
    val wallMap : ArrayList<Title> = arrayListOf()
    var random = Random
    init{
        titles = arrayOf(
            Title().apply {bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.map_floor_1)},
            Title().apply {bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.map_wall_1)},
            Title().apply {bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.map_floor_2)},
            Title().apply {bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.map_wall_2)},
        )

        val levelString = context.assets.open("level1.txt").bufferedReader().use { it.readText() }
// split the string into lines
        val lines = levelString.trim().split("\n")
// create the 2D array
        lines.forEachIndexed{ rowIndex, row ->
            row.forEachIndexed{ columnIndex, column ->
               val posX = columnIndex * 50
               val posY = rowIndex * 50

               map.add(
                   Title().apply {
                       bitmap = titles.get(column.toString().toInt() - 1).bitmap
                       x = posX.toFloat()
                       y = posY.toFloat()
                       isPhasing = if (column != '2') true else false
                   }
               )
                if (column == '2'){
                    wallMap.add(
                        Title().apply {
                            bitmap = titles.get(column.toString().toInt() - 1).bitmap
                            x = posX.toFloat()
                            y = posY.toFloat()
                            isPhasing = false
                        }
                    )
                }
            }
        }
    }

    fun draw(canvas: Canvas){
        for (tile in map){
            canvas.drawBitmap(tile.bitmap, null, tile.getRect(), null)
        }
    }
    fun drawlayer2(canvas: Canvas){
        for (tile in wallMap){
                val d = tile.getRect()
                val dst = Rect(d.left, d.top - d.height() / 2, d.right, d.bottom - d.height() / 2 )
                canvas.drawBitmap(titles.get(3).bitmap, null, dst, null)
        }
    }

 }