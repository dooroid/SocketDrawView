package com.skb.duhui.socketdrawviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import com.skb.duhui.socketdrawview.DrawView

class MainActivity : AppCompatActivity() {

    lateinit var drawView: DrawView
    lateinit var cancelButton: Button
    lateinit var eraseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawView = findViewById(R.id.draw_view)
        cancelButton = findViewById(R.id.cancel_button)
        eraseButton = findViewById(R.id.erase_button)

        cancelButton.setOnClickListener { drawView.cancel() }
        eraseButton.setOnClickListener { drawView.erase() }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        drawView.draw(event!!)
        return super.onTouchEvent(event)
    }
}
