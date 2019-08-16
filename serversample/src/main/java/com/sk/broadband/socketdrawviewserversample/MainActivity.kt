package com.sk.broadband.socketdrawviewserversample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.sk.broadband.socketdrawview.server.ServerSocketDrawView

class MainActivity : AppCompatActivity() {

    lateinit var serverSocketDrawView: ServerSocketDrawView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serverSocketDrawView = findViewById(R.id.draw_view)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        serverSocketDrawView.draw(event!!)
        return super.onTouchEvent(event)
    }
}
