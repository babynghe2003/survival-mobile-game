package com.example.gamemobile2d.map

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.example.gamemobile2d.R
import java.io.File

class Map(private val context: Context) {
    private val title_size: Int = 50
    val titles: Array<Title>
    val map: Array<Array<Title>>
    init{
        titles = arrayOf(
            Title().apply {bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.map_floor_1)},
            Title().apply {bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.map_floor_2)},
            Title().apply {bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.map_wall_1)},
            Title().apply {bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.map_wall_2)},
        )

        val levelString = context.assets.open("level1.txt").bufferedReader().use { it.readText() }
// split the string into lines
        val lines = levelString.trim().split("\n")

// create the 2D array
        map = Array(lines.size) { i ->
            val line = lines[i].trim()
            Array(line.length) { j ->
                when (line[j]) {
                    '1' -> titles?.get(0) // create a Title with ID 0 for '1'
                    '2' -> titles?.get(1) // create a Title with ID 1 for '2'
                    '3' -> titles?.get(2)  // create a Tile with ID 2 for '3'
                    '4' -> titles?.get(3) // create a Title with ID 3 for '4'
                    else -> throw IllegalArgumentException("Invalid character in map: ${line[j]}")
                }
            }
        }

    }

    fun draw(canvas: Canvas){

        map.forEachIndexed {rowIndex, row ->
            row.forEachIndexed{
                columnIndex, column ->
                val x = columnIndex * title_size
                val y = rowIndex * title_size
                val desRect = Rect(x, y, x + title_size, y + title_size)
                canvas.drawBitmap(column.bitmap!!, null, desRect, null)
            }
        }
    }

}