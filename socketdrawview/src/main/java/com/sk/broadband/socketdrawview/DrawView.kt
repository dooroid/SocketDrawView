package com.sk.broadband.socketdrawview

import android.content.Context
import android.graphics.*
import android.support.annotation.VisibleForTesting
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.sk.broadband.socketdrawview.utils.Exception
import java.util.*


open class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs),
    DrawContract.View {

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

    override var pathColor: Int = Color.GREEN
        set(color) {
            field = color
            paintPath.color = field
        }

    override var pathStrokeWidth: Float = 12f
        set(strokeWidth) {
            field = strokeWidth
            paintPath.strokeWidth = field
        }

    override var isDisabled: Boolean = false
    override var isPath: Boolean = true

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

    override fun draw(event: MotionEvent) {
        val curX = coordinateX(event.x)
        val curY = coordinateY(event.y)

        draw(DrawContract.View.TouchInfo(curX, curY, event.action))
    }

    open fun draw(touchInfo: DrawContract.View.TouchInfo) {
        if (!isPath) {
            updateCoordinates(touchInfo.x, touchInfo.y)
            invalidate()
            return
        }

        when (touchInfo.action) {
            MotionEvent.ACTION_DOWN -> {
                startDraw(touchInfo.x, touchInfo.y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                if (isTolerant(touchInfo.x, touchInfo.y)) {
                    recordDraw(touchInfo.x, touchInfo.y)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                commitDraw()
                invalidate()
            }
        }
    }

    override fun undoPrev() {
        if (prevPathStack.isEmpty()) {
            initDraw()
        } else {
            prevPathBitmap = prevPathStack.pop()
            prevPathCanvas = Canvas(prevPathBitmap!!)
        }
        invalidate()
    }

    override fun eraseAll() {
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