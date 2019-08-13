package com.sk.broadband.socketdrawview

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

    var isDisabled: Boolean = false
    var isPath: Boolean = true

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

        if (isDisabled) {
            return
        }

        if (isPath) {
            canvas?.drawBitmap(prevPathBitmap!!, 0f, 0f, paintPath) ?: Exception.SystemCanvasNullException()
            canvas?.drawPath(currentPath, paintPath) ?: Exception.SystemCanvasNullException()
        } else {
            canvas?.drawCircle(prevX, prevY, 40f, paintPath) ?: Exception.SystemCanvasNullException()
        }
    }

    fun draw(event: MotionEvent) {
        val curX = coordinateX(event.x)
        val curY = coordinateY(event.y)

        if (!isPath) {
            updateCoordinates(curX, curY)
            invalidate()
            return
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startDraw(curX, curY)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                if (isTolerant(curX, curY)) {
                    recordDraw(curX, curY)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                commitDraw()
                invalidate()
            }
        }
    }

    fun undoPrev() {
        if (prevPathStack.isEmpty()) {
            initDraw()
        } else {
            prevPathBitmap = prevPathStack.pop()
            prevPathCanvas = Canvas(prevPathBitmap!!)
        }
        invalidate()
    }

    fun eraseAll() {
        initDraw()
        invalidate()
    }


    @VisibleForTesting
    fun coordinateX(curX: Float): Float {
        val location = IntArray(2)
        getLocationInWindow(location)
        return curX - location[0].toFloat()
    }

    @VisibleForTesting
    fun coordinateY(curY: Float): Float {
        val location = IntArray(2)
        getLocationInWindow(location)
        return curY - location[1].toFloat()
    }

    @VisibleForTesting
    fun startDraw(curX: Float, curY: Float) {
        currentPath.moveTo(curX, curY)
        updateCoordinates(curX, curY)
    }

    @VisibleForTesting
    fun recordDraw(curX: Float, curY: Float) {
        currentPath.quadTo(prevX, prevY, curX, curY)
        updateCoordinates(curX, curY)
    }

    private fun updateCoordinates(curX: Float, curY: Float) {
        prevX = curX
        prevY = curY
    }

    @VisibleForTesting
    fun isTolerant(curX: Float, curY: Float): Boolean {
        return Math.abs(curX-prevX) >= pathStrokeWidth || Math.abs(curY-prevY) >= pathStrokeWidth
    }

    @VisibleForTesting
    fun commitDraw() {
        currentPath.lineTo(prevX, prevY)
        prevPathStack.add(Bitmap.createBitmap(prevPathBitmap!!))
        prevPathCanvas?.drawPath(currentPath, paintPath) ?: Exception.TemporaryCanvasNullException()
        currentPath.reset()
    }

    private fun initDraw() {
        prevPathBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        prevPathCanvas = Canvas(prevPathBitmap!!)
    }
}