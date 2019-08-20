package com.sk.broadband.socketdrawview

import android.view.MotionEvent

interface DrawContract {

    interface View {

        data class TouchInfo(var x: Float, var y: Float, var action: Int)

        var pathColor: Int

        var pathStrokeWidth: Float

        var isDisabled: Boolean

        var isPath: Boolean

        fun draw(event: MotionEvent)

        fun undoPrev()

        fun eraseAll()
    }
}