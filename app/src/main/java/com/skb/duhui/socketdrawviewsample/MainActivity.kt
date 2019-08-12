package com.skb.duhui.socketdrawviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.SeekBar
import com.skb.duhui.socketdrawview.DrawView

class MainActivity : AppCompatActivity() {

    lateinit var drawView: DrawView
    lateinit var cancelButton: Button
    lateinit var eraseButton: Button
    lateinit var modeButton: Button

    lateinit var redBar: SeekBar
    lateinit var greenBar: SeekBar
    lateinit var blueBar: SeekBar
    lateinit var alphaBar: SeekBar
    lateinit var widthBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawView = findViewById(R.id.draw_view)
        cancelButton = findViewById(R.id.cancel_button)
        eraseButton = findViewById(R.id.erase_button)
        modeButton = findViewById(R.id.mode_button)

        redBar = findViewById(R.id.red_bar)
        greenBar = findViewById(R.id.green_bar)
        blueBar = findViewById(R.id.blue_bar)
        alphaBar = findViewById(R.id.alpha_bar)
        widthBar = findViewById(R.id.width_bar)

        cancelButton.setOnClickListener { drawView.cancel() }
        eraseButton.setOnClickListener { drawView.erase() }

        widthBar.progress = drawView.pathStrokeWidth.toInt()
        widthBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                drawView.pathStrokeWidth = i.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // onStart
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // onStop
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        drawView.draw(event!!)
        return super.onTouchEvent(event)
    }
}
