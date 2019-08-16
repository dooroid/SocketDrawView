package com.sk.broadband.socketdrawview

import android.view.MotionEvent

interface DrawContract {

    interface View {
        var pathColor: Int

        var pathStrokeWidth: Float

        var isDisabled: Boolean

        var isPath: Boolean

        fun draw(event: MotionEvent)

        fun undoPrev()

        fun eraseAll()
    }
}