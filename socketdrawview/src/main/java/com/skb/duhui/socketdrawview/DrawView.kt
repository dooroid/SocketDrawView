package com.skb.duhui.socketdrawview

import android.content.Context
import android.graphics.*
import android.support.annotation.VisibleForTesting
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*

class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paintPath: Paint = Paint()
    private val currentPath: Path = Path()

    @VisibleForTesting
    val prevPathStack: Stack<Bitmap> = Stack()
    @VisibleForTesting
    var prevPathBitmap: Bitmap? = null
    @VisibleForTesting
    var prevPathCanvas: Canvas? = null

    private var prevX: Float = 0f
    private var prevY: Float = 0f

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

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initDraw()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawBitmap(prevPathBitmap!!, 0f, 0f, paintPath)
        canvas.drawPath(currentPath, paintPath)
    }

    fun draw(event: MotionEvent) {
        val x = coordinateX(event.x)
        val y = coordinateY(event.y)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startDraw(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                if (isTolerant(x, y)) {
                    recordDraw(x, y)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                commitDraw()
                invalidate()
            }
        }
    }

    fun cancel() {
        if (prevPathStack.isEmpty()) {
            initDraw()
        } else {
            prevPathBitmap = prevPathStack.pop()
            prevPathCanvas = Canvas(prevPathBitmap!!)
        }
        invalidate()
    }

    fun erase() {
        initDraw()
        invalidate()
    }


    @VisibleForTesting
    fun coordinateX(x: Float): Float {
        val location = IntArray(2)
        getLocationInWindow(location)
        return x - location[0].toFloat()
    }

    @VisibleForTesting
    fun coordinateY(y: Float): Float {
        val location = IntArray(2)
        getLocationInWindow(location)
        return y - location[1].toFloat()
    }

    @VisibleForTesting
    fun startDraw(x: Float, y: Float) {
        currentPath.moveTo(x, y)
        prevX = x
        prevY = y
    }

    @VisibleForTesting
    fun recordDraw(x: Float, y: Float) {
        currentPath.quadTo(prevX, prevY, x, y)
        prevX = x
        prevY = y
    }

    @VisibleForTesting
    fun isTolerant(x: Float, y: Float): Boolean {
        return Math.abs(x-prevX) >= pathStrokeWidth || Math.abs(y-prevY) >= pathStrokeWidth
    }

    @VisibleForTesting
    fun commitDraw() {
        currentPath.lineTo(prevX, prevY)
        prevPathStack.add(Bitmap.createBitmap(prevPathBitmap!!))
        prevPathCanvas!!.drawPath(currentPath, paintPath)
        currentPath.reset()
    }

    private fun initDraw() {
        prevPathBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        prevPathCanvas = Canvas(prevPathBitmap!!)
    }
}