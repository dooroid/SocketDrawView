package com.skb.duhui.socketdrawview

import android.content.Context
import android.util.AttributeSet
import org.junit.Before
import org.mockito.Mock
import android.graphics.Color
import android.graphics.Paint
import org.junit.Test
import org.mockito.MockitoAnnotations



class DrawViewUnitTest {

    @Mock
    private val context: Context? = null
    @Mock
    private val attrs: AttributeSet? = null

    private lateinit var drawView: DrawView

    @Before
    fun createDrawView() {
        MockitoAnnotations.initMocks(this)
        drawView = DrawView(context!!, attrs!!)
    }

    @Test
    fun initVariables() {
        assert(drawView.pathColor == Color.GREEN)
        assert(drawView.pathStrokeWidth == 12f)
    }
}