package com.sk.broadband.socketdrawview.server

import com.sk.broadband.socketdrawview.DrawContract

interface ServerSocketContract {
    interface View : DrawContract.View {

        fun createServerSocket(port: Int)

        fun disconnectServer()

        fun readBuffer()
    }
}