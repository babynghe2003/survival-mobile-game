package com.example.gamemobile2d.map

import android.graphics.Bitmap
import android.graphics.Rect

class Title() {
    // 0 -> space
    // 1 -> floor
    // 2 -> wall
    // 3 -> tree
    // 4 -> trap
    var bitmap: Bitmap? = null
    var rect: Rect? = null
    var collision = false

}