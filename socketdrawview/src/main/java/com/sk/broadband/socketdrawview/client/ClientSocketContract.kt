package com.sk.broadband.socketdrawview.client

import android.view.MotionEvent
import com.sk.broadband.socketdrawview.DrawContract
import java.lang.Exception

interface ClientSocketContract {
    interface View : DrawContract.View {

        fun connectSocket(ip: String, port: Int = 3000)

        fun disconnectClient()

        fun drawServer(event: MotionEvent)
    }
}