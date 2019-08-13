package com.sk.broadband.socketdrawview.client

import com.sk.broadband.socketdrawview.DrawContract

interface ClientSocketContract {
    interface View : DrawContract.View {

        fun connectSocket(ip: String, port: Int)

        fun disconnectClient()

        fun writeBuffer(data: ByteArray)
    }
}