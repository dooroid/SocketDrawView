package com.skb.duhui.socketdrawview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paintPath: Paint = Paint()

    var pathColor: Int = Color.GREEN
        set(color) {
            field = color
            paintPath.color = field
        }

    var pathStrokeWidth: Float = 12f
        set(strokeWidth) {
            field = strokeWidth
            paintPath.strokeWidth = field
        }

    init {
        paintPath.isAntiAlias = true
        paintPath.isDither = true
        paintPath.color = pathColor
        paintPath.strokeWidth = pathStrokeWidth
        paintPath.style = Paint.Style.STROKE
        paintPath.strokeJoin = Paint.Join.ROUND
        paintPath.strokeCap = Paint.Cap.ROUND
    }

}