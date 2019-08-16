package com.sk.broadband.socketdrawview.server

import com.sk.broadband.socketdrawview.DrawContract

interface ServerSocketContract {
    interface View : DrawContract.View {

        var onClosedListener: (()->Unit)?

        fun createServerSocket(port: Int = 3000)

        fun disconnectServer()

        fun getIPAddress(): String
    }
}