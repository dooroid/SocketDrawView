package com.skb.duhui.socketdrawviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.skb.duhui.socketdrawview.DrawView

class MainActivity : AppCompatActivity() {

    lateinit var drawView: DrawView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawView = findViewById(R.id.draw_view)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        drawView.draw(event!!)
        return super.onTouchEvent(event)
    }
}
