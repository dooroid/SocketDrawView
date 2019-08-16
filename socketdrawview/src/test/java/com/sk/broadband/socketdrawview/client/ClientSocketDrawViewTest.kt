package com.sk.broadband.socketdrawview.client

import android.content.Context
import android.util.AttributeSet
import org.junit.Before
import org.mockito.*


class ClientSocketDrawViewTest {

    @InjectMocks
    lateinit var drawView: ClientSocketDrawView
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var attrs: AttributeSet

    @Before
    fun createDrawView() {
        MockitoAnnotations.initMocks(this)
    }
}