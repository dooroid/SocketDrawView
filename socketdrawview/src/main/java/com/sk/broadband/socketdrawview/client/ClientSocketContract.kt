package com.sk.broadband.socketdrawview.client

import android.view.MotionEvent
import com.sk.broadband.socketdrawview.DrawContract

interface ClientSocketContract {
    interface View : DrawContract.View {

        var onClosedListener: (()->Unit)?
        var onIOExceptionListener: (()->Unit)?
        var onSocketTimeoutExceptionListener: (()->Unit)?
        var onIllegalBlockingModeExceptionListener: (()->Unit)?
        var onIllegalArgumentExceptionListener: (()->Unit)?

        fun connectSocket(ip: String, port: Int = 3000)

        fun disconnectClient()

        fun drawServer(event: MotionEvent)

        fun undoPrevServer()

        fun eraseAllServer()
    }
}