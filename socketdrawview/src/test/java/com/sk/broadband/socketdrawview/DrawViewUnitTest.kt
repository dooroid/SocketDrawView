package com.sk.broadband.socketdrawview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import org.junit.Before
import android.graphics.Color
import android.view.MotionEvent
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*


class DrawViewUnitTest {

    @InjectMocks
    lateinit var drawView: DrawView
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var attrs: AttributeSet

    @Before
    fun createDrawView() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun initVariables() {
        assert(drawView.pathColor == Color.GREEN)
        assert(drawView.pathStrokeWidth == 12f)
    }

    @Test
    fun draw() {
        val mockEvent: MotionEvent = mock(MotionEvent::class.java)
        val spyDrawView = spy(drawView)

        spyDrawView.draw(mockEvent)

        verify(spyDrawView, times(1)).coordinateX(mockEvent.x)
        verify(spyDrawView, times(1)).coordinateY(mockEvent.y)
    }

    @Test
    fun drawActionDown() {
        val mockEvent: MotionEvent = mock(MotionEvent::class.java)
        val spyDrawView = spy(drawView)

        Mockito.`when`(mockEvent.action).thenReturn(MotionEvent.ACTION_DOWN)
        spyDrawView.draw(mockEvent)

        verify(spyDrawView, times(1))
            .startDraw(ArgumentMatchers.anyFloat(), ArgumentMatchers.anyFloat())
        verify(spyDrawView, times(1)).invalidate()
    }

    @Test
    fun drawActionMove_tolerant() {
        val mockEvent: MotionEvent = mock(MotionEvent::class.java)
        val spyDrawView = spy(drawView)

        Mockito.`when`(mockEvent.action).thenReturn(MotionEvent.ACTION_MOVE)
        Mockito.`when`(spyDrawView.isTolerant(ArgumentMatchers.anyFloat(), ArgumentMatchers.anyFloat()))
            .thenReturn(true)
        spyDrawView.draw(mockEvent)

        verify(spyDrawView, times(1))
            .recordDraw(ArgumentMatchers.anyFloat(), ArgumentMatchers.anyFloat())
        verify(spyDrawView, times(1)).invalidate()
    }

    @Test
    fun drawActionMove_notTolerant() {
        val mockEvent: MotionEvent = mock(MotionEvent::class.java)
        val spyDrawView = spy(drawView)

        Mockito.`when`(mockEvent.action).thenReturn(MotionEvent.ACTION_MOVE)
        Mockito.`when`(spyDrawView.isTolerant(ArgumentMatchers.anyFloat(), ArgumentMatchers.anyFloat()))
            .thenReturn(false)
        spyDrawView.draw(mockEvent)

        verify(spyDrawView, times(0))
            .recordDraw(ArgumentMatchers.anyFloat(), ArgumentMatchers.anyFloat())
        verify(spyDrawView, times(0)).invalidate()
    }

    @Test
    fun drawActionUp() {
        val mockEvent: MotionEvent = mock(MotionEvent::class.java)
        val spyDrawView = spy(drawView)

        Mockito.`when`(mockEvent.action).thenReturn(MotionEvent.ACTION_UP)
        spyDrawView.prevPathCanvas = mock(Canvas::class.java)
        spyDrawView.prevPathBitmap = mock(Bitmap::class.java)
        spyDrawView.draw(mockEvent)

        verify(spyDrawView, times(1))
            .commitDraw()
        verify(spyDrawView, times(1)).invalidate()
    }

    @Test
    fun commit_checkStackSize() {
        drawView.prevPathCanvas = mock(Canvas::class.java)
        drawView.prevPathBitmap = mock(Bitmap::class.java)
        drawView.commitDraw()
        drawView.commitDraw()
        drawView.commitDraw()
        drawView.commitDraw()
        drawView.commitDraw()
        drawView.commitDraw()
        drawView.commitDraw()
        assert(drawView.prevPathStack.size == 7)
    }

    @Test
    fun cancel_checkStackSize() {
        drawView.prevPathStack.add(mock(Bitmap::class.java))
        drawView.prevPathStack.add(mock(Bitmap::class.java))
        drawView.prevPathStack.add(mock(Bitmap::class.java))
        drawView.prevPathStack.add(mock(Bitmap::class.java))
        drawView.prevPathStack.add(mock(Bitmap::class.java))
        drawView.prevPathStack.add(mock(Bitmap::class.java))
        drawView.prevPathStack.add(mock(Bitmap::class.java))
        drawView.prevPathStack.add(mock(Bitmap::class.java))

        drawView.cancel()
        drawView.cancel()
        drawView.cancel()

        assert(drawView.prevPathStack.size == 5)
    }
}