package com.sk.broadband.socketdrawview.server

import android.content.Context
import android.util.AttributeSet
import org.junit.Before
import org.mockito.*


class ServerSocketDrawViewTest {

    @InjectMocks
    lateinit var drawView: ServerSocketDrawView
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var attrs: AttributeSet

    @Before
    fun createDrawView() {
        MockitoAnnotations.initMocks(this)
    }
}