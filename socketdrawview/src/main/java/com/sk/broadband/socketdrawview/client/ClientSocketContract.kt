package com.sk.broadband.socketdrawview.client

import android.view.MotionEvent
import com.sk.broadband.socketdrawview.DrawContract

interface ClientSocketContract {
    interface View : DrawContract.View {

        fun connectSocket(ip: String, port: Int)

        fun disconnectClient()

        fun drawServer(event: MotionEvent)
    }
}